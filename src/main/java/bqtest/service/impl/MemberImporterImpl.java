package bqtest.service.impl;

import bqtest.service.Member;
import bqtest.service.MemberImporter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MemberImporterImpl implements MemberImporter {

    public List<Member> importMembers(File inputFile) throws IOException {
        return Files.lines(inputFile.toPath())
                .map(line -> {
                   //TODO implement here
                    String[] fields = line.split("\\s+");

                    Member member = new Member();
                    member.setId(fields[0]);
                    member.setFirstName(fields[2]);
                    member.setLastName(fields[1]);
                    member.setAddress(fields[3] + " " + fields[4] + " " + fields[5]);

                    if (fields.length == 11) member.setCity(fields[6] + " " + fields[7] + " " + fields[8]);
                    if (fields.length == 10) member.setCity(fields[6] + " " + fields[7]);
                    if (fields.length == 9) member.setCity(fields[6]);

                    String stateStr = fields[fields.length -2];
                    member.setState(stateStr.length() > 2 ? stateStr.substring(stateStr.length() - 2) : stateStr);

                    member.setZip(fields[fields.length -1]);

                    return member;
                }).collect(Collectors.toList());
    }

}
