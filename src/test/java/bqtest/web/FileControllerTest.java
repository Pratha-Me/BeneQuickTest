package bqtest.web;

import bqtest.service.Member;
import bqtest.service.MemberFileProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileController.class)
//@ExtendWith({SpringExtension.class})
//@AutoConfigureWebMvc
class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberFileProcessor memberFileProcessor;

    @Test
    void loadData() throws Exception {
        try {
            Map<String, List<Member>> groupedMembers = new HashMap<>();
            Member testMember1 = new Member();
            testMember1.setId("5263727");
            testMember1.setFirstName("Test1");
            testMember1.setLastName("Last1");
            testMember1.setAddress("address1");
            testMember1.setCity("city1");
            testMember1.setState("MN");
            testMember1.setZip("98863");

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

            List<Member> memberList1 = new ArrayList<>();
            memberList1.add(testMember1);
            memberList1.add(testMember2);

            List<Member> memberList2 = new ArrayList<>();
            memberList2.add(testMember3);

            groupedMembers.put("MN", memberList1);
            groupedMembers.put("NY", memberList2);

            given(memberFileProcessor.processFile(new File("Members.txt"))).willReturn(groupedMembers);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/load-data"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.MN[1].id").value("675126"))
                .andExpect(jsonPath("$.MN", hasSize(2)))
                .andExpect(jsonPath("$.NY[0].id").value("26633999"))
                .andExpect(jsonPath("$.NY", hasSize(1)));
    }
}