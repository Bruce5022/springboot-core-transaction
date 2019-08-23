package com.sky.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(User user) {
        // 不空用传来的值新增
        String sql = "insert into test_user(name,email,birthday,sex) values(?,?,?,?)";
        if (!ObjectUtils.isEmpty(user)) {
            jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getBirthday(), user.getSex());
            return;
        }

        //传来为空,自动拼接
        RowMapper<User> userRowMapper = new UserRowMapper();
        List<User> query = jdbcTemplate.query("select * from test_user", userRowMapper);
        int size = query.size();
        String name = "user" + size;
        String email = "user" + size + "@aliyun.com";

        jdbcTemplate.update(sql, name, email, new Date(), 1);

    }


    public class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setBirthday(rs.getDate("birthday"));
            return user;
        }

    }
}
