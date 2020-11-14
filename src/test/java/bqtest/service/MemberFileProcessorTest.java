package bqtest.service;

import bqtest.service.impl.MemberFileProcessorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class MemberFileProcessorTest {

    @TestConfiguration
    public static class MemberFileProcessorBeanConfig {

        @Bean
        public MemberFileProcessor makeProcessorBean(){
            return new MemberFileProcessorImpl();
        }

//        @Bean
//        public MemberImporter makeImporterBean(){
//            return new MemberImporterImpl();
//        }
    }

    private static List<Member> memberListDuplicate = null;

    private static List<Member> memberListSplit = null;

    @Autowired
    private MemberFileProcessor memberFileProcessor;

//    @Autowired
//    private MemberImporter memberImporter;

    @BeforeEach
    public void setup(){
        Member testMember1 = new Member();
        testMember1.setId("5263727");
        testMember1.setFirstName("Test1");
        testMember1.setLastName("Last1");
        testMember1.setAddress("address1");
        testMember1.setCity("city1");
        testMember1.setState("MN");
        testMember1.setZip("98863");

        Member testMember4 = new Member();
        testMember4.setId("5263727");
        testMember4.setFirstName("Test1");
        testMember4.setLastName("Last1");
        testMember4.setAddress("address1");
        testMember4.setCity("city1");
        testMember4.setState("MN");
        testMember4.setZip("98863");

        Member testMember2 = new Member();
        testMember2.setId("675126");
        testMember2.setFirstName("Test2");
        testMember2.setLastName("Last2");
        testMember2.setAddress("address2");
        testMember2.setCity("city2");
        testMember2.setState("MN");
        testMember2.setZip("87666");

        Member testMember3 = new Member();
        testMember3.setId("26633999");
        testMember3.setFirstName("Test3");
        testMember3.setLastName("Last3");
        testMember3.setAddress("address3");
        testMember3.setCity("city3");
        testMember3.setState("NY");
        testMember3.setZip("64433");

        memberListDuplicate = new ArrayList<>();
        memberListDuplicate.add(testMember1);
        memberListDuplicate.add(testMember4);
        memberListDuplicate.add(testMember2);
        memberListDuplicate.add(testMember3);

        Member testMember5 = new Member();
        testMember5.setId("456625499");
        testMember5.setFirstName("Test5");
        testMember5.setLastName("Last5");
        testMember5.setAddress("address5");
        testMember5.setCity("city5");
        testMember5.setState("OK");
        testMember5.setZip("213233");

        Member testMember6 = new Member();
        testMember6.setId("885633999");
        testMember6.setFirstName("Test6");
        testMember6.setLastName("Last6");
        testMember6.setAddress("address6");
        testMember6.setCity("city6");
        testMember6.setState("OK");
        testMember6.setZip("986403");

        Member testMember7 = new Member();
        testMember7.setId("3393116099");
        testMember7.setFirstName("Test7");
        testMember7.setLastName("Last7");
        testMember7.setAddress("address7");
        testMember7.setCity("city7");
        testMember7.setState("NY");
        testMember7.setZip("604033");

        Member testMember8 = new Member();
        testMember8.setId("1190630900");
        testMember8.setFirstName("Test8");
        testMember8.setLastName("Last8");
        testMember8.setAddress("address8");
        testMember8.setCity("city8");
        testMember8.setState("OK");
        testMember8.setZip("610403");

        memberListSplit = new ArrayList<>();
        memberListSplit.add(testMember5);
        memberListSplit.add(testMember6);
        memberListSplit.add(testMember7);
        memberListSplit.add(testMember8);

//        try {
//            when(memberImporter.importMembers(new File("Member.txt"))).thenReturn(memberListDuplicate);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Test
    void getNonDuplicateMembers() {
        List<Member> members = memberFileProcessor.getNonDuplicateMembers(memberListDuplicate);
        assertThat(members.size()).isEqualTo(3);
        assertThat(
                members.stream()
                .filter(member -> member.getState().equals("MN"))
                .collect(Collectors.toList()).size()
        ).isEqualTo(2);
    }

    @Test
    void splitMembersByState() {
        Map<String, List<Member>> groupedMembers = memberFileProcessor.splitMembersByState(memberListSplit);

        List<String> stateKey = new ArrayList<>();
        stateKey.add("OK");
        stateKey.add("NY");

        assertThat(groupedMembers).isInstanceOf(Map.class);
        assertThat(groupedMembers.keySet().containsAll(stateKey)).isTrue();

        assertThat(groupedMembers.get("OK").size()).isEqualTo(3);
        assertThat(groupedMembers.get("OK").get(2).getFirstName()).isNotEmpty().isEqualTo("Test8");
        assertThat(groupedMembers.get("OK").get(2).getId()).isNotEmpty().isEqualTo("1190630900");

        assertThat(groupedMembers.get("NY").size()).isEqualTo(1);
        assertThat(groupedMembers.get("NY").get(0).getFirstName()).isNotEmpty().isEqualTo("Test7");
        assertThat(groupedMembers.get("NY").get(0).getId()).isNotEmpty().isEqualTo("3393116099");
    }
}