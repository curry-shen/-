package Mockito���ɲ���;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


public class StuControllerTest {

	StuController mController;
    StudentDAO mStuDao;

    @Before
    public void setUp() throws Exception {
        mController = new StuController();
        mStuDao = mock(StudentDAO.class);
        mController.setStudentDAO(mStuDao);
    }

    @Test
    public void testGetStudentInfo() {
        Student returnStudent = new Student();
        returnStudent.id = 123;
        returnStudent.name = "mock-user";

        // ����getStudentFromDBʱ����returnStudent����
        when(mStuDao.getStudentFromDB(anyInt())).thenReturn(returnStudent);
        // ����getStudentInfo
        Student student = mController.getStudentInfo(123);
        // ��֤����
        assertEquals(student.id, 123);
        assertEquals(student.name, "mock-user");
    }

    @Test
    public void testGetStudentInfoFromServer() {
        // ����getStudentFromDBʱ����null
        when(mStuDao.getStudentFromDB(anyInt())).thenReturn(null);
        // ����getStudentInfo
        Student student = mController.getStudentInfo(456);
        // ��֤����
        assertEquals(student.id, 456);
        assertEquals(student.name, "server-user");
    }

    @Test
    public void testCaptureParam() {
        // ע��: ����һ��mock����
        StuController mockController = mock(StuController.class) ;

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                int studentId = (Integer)invocation.getArguments()[0] ;
                System.out.println("ѧ��id : " + studentId);
                assertEquals(666, studentId);
                return null;
            }
        }).when(mockController).getStudentInfo(anyInt()) ;

        mockController.getStudentInfo(666) ;
    }

	
}
