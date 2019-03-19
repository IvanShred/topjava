package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private void insertRoles(User user) {
        List<User> userWithRole = new ArrayList<>();
        if (user.getRoles().size() > 1) {
            for (Role role : user.getRoles()) {
                userWithRole.add(new User(user.getId(), user.getName(), user.getEmail(), user.getPassword(), role));
            }
        } else {
            userWithRole.add(user);
        }

        jdbcTemplate.batchUpdate("INSERT INTO user_roles " +
                "(user_id, role) " +
                "VALUES (?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                User user = userWithRole.get(i);
                ps.setInt(1, user.getId());
                ps.setString(2, user.getRoles().iterator().next().name());
            }

            @Override
            public int getBatchSize() {
                return userWithRole.size();
            }
        });
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            if (user.getRoles() != null && user.getRoles().size() != 0) {
                insertRoles(user);
            }
        } else {
            boolean isUpdated = namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) != 0;
            Set one = user.getRoles();
            Set two = get(user.getId()).getRoles();
            one.removeAll(two);
            two.removeAll(one);
            boolean isDifferentRoles = !(one.isEmpty() && two.isEmpty());
            if (!isUpdated && !isDifferentRoles) {
                return null;
            } else if (isDifferentRoles) {
                jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
                if (user.getRoles() != null && user.getRoles().size() != 0) {
                    insertRoles(user);
                }
            }
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT u.id as id, u.name as name, u.email as email, " +
                        "u.password as password, u.enabled as enabled, u.registered as registered, " +
                        "r.role as roles, u.calories_per_day as caloriesPerDay " +
                        "FROM users u " +
                        "LEFT JOIN user_roles r ON u.id = r.user_id " +
                        "WHERE u.id=?",
                ROW_MAPPER, id);
        return DataAccessUtils.singleResult(getAllWithRoles(users));
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT u.id as id, u.name as name, u.email as email, " +
                        "u.password as password, u.enabled as enabled, u.registered as registered, " +
                        "r.role as roles, u.calories_per_day as caloriesPerDay " +
                        "FROM users u " +
                        "LEFT JOIN user_roles r ON u.id = r.user_id " +
                        "WHERE email=?",
                ROW_MAPPER, email);
        return DataAccessUtils.singleResult(getAllWithRoles(users));
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT u.id as id, u.name as name, u.email as email, " +
                        "u.password as password, u.enabled as enabled, u.registered as registered, " +
                        "r.role as roles, u.calories_per_day as caloriesPerDay " +
                        "FROM users u " +
                        "LEFT JOIN user_roles r ON u.id = r.user_id " +
                        "ORDER BY name, email",
                ROW_MAPPER);
        return getAllWithRoles(users);
    }

    private List<User> getAllWithRoles(List<User> users) {
        Map<User, Set<Role>> usersWithRoles = new LinkedHashMap<>();
        for (User user : users) {
            if (user.getRoles() != null && user.getRoles().size() != 0) {
                usersWithRoles.computeIfAbsent(user, k -> EnumSet.noneOf(Role.class)).add(user.getRoles().iterator().next());
            } else {
                usersWithRoles.put(user, EnumSet.noneOf(Role.class));
            }
        }
        List<User> results = new ArrayList<>();
        for (Map.Entry<User, Set<Role>> entry : usersWithRoles.entrySet()) {
            User user = entry.getKey();
            user.setRoles(entry.getValue());
            results.add(user);
        }
        return results;
    }
}
