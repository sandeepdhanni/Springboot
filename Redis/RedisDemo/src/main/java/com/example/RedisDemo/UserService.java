package com.example.RedisDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserService {

        public static final String HASH_KEY = "user";

        @Autowired
        private RedisTemplate<String, Object> redisTemplate;

        public User save(User user){
            redisTemplate.opsForHash().put(HASH_KEY, user.getId(), user);
            return user;
        }

        public List<User> findAll(){
            return redisTemplate.opsForHash().values(HASH_KEY)
                    .stream()
                    .map(obj -> (User) obj)
                    .toList();
        }

        public User findProductById(String id){
            return (User) redisTemplate.opsForHash().get(HASH_KEY, id);
        }

        public String deleteProduct(String id){
            redisTemplate.opsForHash().delete(HASH_KEY, id);
            return "user removed !!";
        }
    }
