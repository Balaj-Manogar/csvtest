package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public void createUsers() {
        int index = 1000;
        for (int i = 0; i < 2000; i++) {
            String name = generateName();
            int id = ++index;
//            System.out.println(id);
            userDao.createUser(new User(id, name));
        }

    }

    public List<User> generateCSV() throws IOException {
        Writer writer = Files.newBufferedWriter(Paths.get("user.csv"));
        CSVWriter csvWriter = new CSVWriter(writer,
                '|',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        String[] headerRecord = {"Id", "Name", "Full name"};
        csvWriter.writeNext(headerRecord);
        List<User> allUsers = userDao.getAllUsers();

        allUsers.stream().forEach(u -> {
            csvWriter.writeNext(new String[]{u.getId().toString(), u.getName(), generateName()});
        });

        writer.close();

        return allUsers;

    }

    @Transactional
    public int updateUser() throws IOException {
        int count = 0;
        Reader reader = Files.newBufferedReader(Paths.get("user.csv"));
        HeaderColumnNameMappingStrategy<User> strategy
                = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(User.class);
        CsvToBean<User> csvToBean = new CsvToBeanBuilder(reader)
                .withType(User.class)
                .withIgnoreEmptyLine(true)
                .withIgnoreLeadingWhiteSpace(true)
                .withSeparator('|')
                .withMappingStrategy(strategy)
                .build();

        Iterator<User> userIterator = csvToBean.iterator();

        while (userIterator.hasNext()) {
            User user = userIterator.next();
            count += userDao.updateUser(user);
        }
        return count;
    }

    private String generateName() {
        String s = "";
        for (int i = 0; i < 4; i++) {
            Random rnd = new Random();
            char c = (char) (rnd.nextInt(26) + 'a');
            s += c;
        }
        return s;
    }


}
