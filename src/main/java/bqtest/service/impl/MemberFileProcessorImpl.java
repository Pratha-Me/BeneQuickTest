package bqtest.service.impl;

import bqtest.service.Member;
import bqtest.service.MemberFileProcessor;
import bqtest.service.MemberImporter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberFileProcessorImpl extends MemberFileProcessor {

    /*
     * Implement methods here
     */

    @Override
    protected MemberImporter getMemberImporter() {
        return null;
    }

    @Override
    protected List<Member> getNonDuplicateMembers(List<Member> membersFromFile) {
        return null;
    }

    @Override
    protected Map<String, List<Member>> splitMembersByState(List<Member> validMembers) {
        return null;
    }

}
