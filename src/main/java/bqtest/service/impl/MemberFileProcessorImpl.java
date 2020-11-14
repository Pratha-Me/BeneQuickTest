package bqtest.service.impl;

import bqtest.service.Member;
import bqtest.service.MemberFileProcessor;
import bqtest.service.MemberImporter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MemberFileProcessorImpl extends MemberFileProcessor {

    /*
     * Implement methods here
     */

    @Override
    protected MemberImporter getMemberImporter() {
        return new MemberImporterImpl();
    }

    @Override
    protected List<Member> getNonDuplicateMembers(List<Member> membersFromFile) {
        Set<Integer> idSet = new HashSet<>();
        membersFromFile.removeIf(member -> !idSet.add(Integer.parseInt(member.getId())));

        return membersFromFile;
    }

    @Override
    protected Map<String, List<Member>> splitMembersByState(List<Member> validMembers) {
        Map<String, List<Member>> membersByStateMap = new HashMap<>();
        Set<String> stateSet = new HashSet<>();

        validMembers.forEach(member -> stateSet.add(member.getState()));
        stateSet.forEach(s -> membersByStateMap.put(s,
                        validMembers.stream()
                                .filter(member -> member.getState().equals(s))
                                .collect(Collectors.toList())));

        return membersByStateMap;
    }

}
