import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

public class FileSystemTest {

    FileSystem FS;

    void setUp() {
        FS = new FileSystem();
    }

    @Test
    public void ConstructorTest() {
        FS = new FileSystem("D:\\UCSD\\12\\pa7\\cse12-pa7-BST\\pa7-starter\\input.txt");
        assertEquals(3, FS.nameTree.size());
        assertEquals(2, FS.dateTree.size());
        assertEquals(2, FS.dateTree.get("2021/02/01").size());
    }

    @Test
    public void addTest() {
        setUp();
        FS.add("mySample.txt", "/home", "2021/02/01");
        String expected = "[mySample.txt: {Name: mySample.txt, Directory: /home, Modified Date: 2021/02/01}]";
        assertEquals(expected, FS.outputNameTree().toString());
        FS.add("mySample1.txt", "/root", "2021/02/01");
        FS.add("mySample2.txt", "/user", "2021/02/06");
        assertEquals(3, FS.nameTree.size());
        assertEquals(2, FS.dateTree.size());
        expected = "[mySample.txt: {Name: mySample.txt, Directory: /home, Modified Date: 2021/02/01}, mySample1.txt: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01}, mySample2.txt: {Name: mySample2.txt, Directory: /user, Modified Date: 2021/02/06}]";
        assertEquals(expected, FS.outputNameTree().toString());
    }

    @Test
    public void addNullTest() {
        setUp();
        FS.add("mySample.txt", "/home", "2021/02/01");
        String expected = "[mySample.txt: {Name: mySample.txt, Directory: /home, Modified Date: 2021/02/01}]";
        assertEquals(expected, FS.outputNameTree().toString());
        FS.add(null,"/home", "2021/02/01");
        FS.add("mySample.txt", null, "2021/02/01");
        FS.add("mySample.txt", "/home", null);
        assertEquals(1, FS.dateTree.size());
    }

    @Test
    public void findFileByDateTest() {
        setUp();
        FS.add("mySample.txt", "/home", "2021/02/01");
        FS.add("mySample1.txt", "/root", "2021/02/01");
        FS.add("mySample2.txt", "/user", "2021/02/06");
        ArrayList<String> expected = new ArrayList<>();
        expected.add("mySample.txt");
        expected.add("mySample1.txt");
        assertEquals(expected, FS.findFileNamesByDate("2021/02/01"));
    }

    @Test
    public void findFileByDateNullTest() {
        setUp();
        FS.add("mySample.txt", "/home", "2021/02/01");
        FS.add("mySample1.txt", "/root", "2021/02/01");
        FS.add("mySample2.txt", "/user", "2021/02/06");
        ArrayList<String> expected = new ArrayList<>();
        assertEquals(null, FS.findFileNamesByDate(null));
        assertEquals(expected, FS.findFileNamesByDate("2022/02/01"));
    }

    @Test
    public void outputNameTreeTest() {
        setUp();
        assertEquals("[]", FS.outputNameTree().toString());
        FS.add("mySample.txt", "/home", "2021/02/01");
        String expected = "[mySample.txt: {Name: mySample.txt, Directory: /home, Modified Date: 2021/02/01}]";
        assertEquals(expected, FS.outputNameTree().toString());
    }

    @Test
    public void outputNameTreeTest2() {
        setUp();
        FS.add("mySample.txt", "/home", "2021/02/01");
        FS.add("mySample1.txt", "/root", "2021/02/01");
        FS.add("mySample.txt", "/documents", "2021/02/06");
        String expected = "[mySample.txt: {Name: mySample.txt, Directory: /documents, Modified Date: 2021/02/06}, mySample1.txt: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01}]";
        assertEquals(expected, FS.outputNameTree().toString());
    }

    @Test
    public void outputDateTreeTest() {
        setUp();
        assertEquals("[]", FS.outputDateTree().toString());
        FS.add("mySample.txt", "/home", "2021/02/01");
        String expected = "[2021/02/01: {Name: mySample.txt, Directory: /home, Modified Date: 2021/02/01}]";
        assertEquals(expected, FS.outputDateTree().toString());
    }

    @Test
    public void outputDateTreeTest2() {
        setUp();
        FS.add("mySample.txt", "/home", "2021/02/01");
        FS.add("mySample1.txt", "/root", "2021/02/01");
        FS.add("mySample2.txt", "/user", "2021/02/06");
        String expected = "[2021/02/06: {Name: mySample2.txt, Directory: /user, Modified Date: 2021/02/06}, 2021/02/01: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01}, 2021/02/01: {Name: mySample.txt, Directory: /home, Modified Date: 2021/02/01}]";
        assertEquals(expected, FS.outputDateTree().toString());
    }

    @Test
    public void outputDateTreeTest3() {
        setUp();
        FS.add("mySample1.txt", "/root", "2021/02/01");
        FS.add("mySample.txt", "/home", "2021/02/01");
        FS.add("mySample2.txt", "/user", "2021/02/06");
        String expected = "[2021/02/06: {Name: mySample2.txt, Directory: /user, Modified Date: 2021/02/06}, 2021/02/01: {Name: mySample.txt, Directory: /home, Modified Date: 2021/02/01}, 2021/02/01: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01}]";
        assertEquals(expected, FS.outputDateTree().toString());
    }

    @Test
    public void dateFilterTest() {
        setUp();
        FS.add("mySample.txt", "/home", "2021/02/01");
        FS.add("mySample1.txt", "/root", "2021/02/01");
        FS.add("mySample2.txt", "/user", "2021/02/06");
        FS.add("mySample3.txt", "/home", "2021/02/04");
        FileSystem newFS = FS.filter("2021/02/01", "2021/02/06");
        String expected = "[2021/02/04: {Name: mySample3.txt, Directory: /home, Modified Date: 2021/02/04}, 2021/02/01: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01}, 2021/02/01: {Name: mySample.txt, Directory: /home, Modified Date: 2021/02/01}]";
        assertEquals(expected, newFS.outputDateTree().toString());
    }

    @Test
    public void dateFilterTest2() {
        setUp();
        FS.add("mySample.txt", "/home", "2021/02/01");
        FS.add("mySample1.txt", "/root", "2021/02/01");
        FS.add("mySample2.txt", "/user", "2021/02/06");
        FS.add("mySample3.txt", "/home", "2021/02/04");
        FileSystem newFS = FS.filter("2021/02/02", "2021/02/04");
        String expected = "[]";
        assertEquals(expected, newFS.outputDateTree().toString());
    }

    @Test
    public void nameFilterTest() {
        setUp();
        FS.add("mySample.txt", "/home", "2021/02/01");
        FS.add("mySample1.txt", "/root", "2021/02/01");
        FS.add("ySample2.txt", "/user", "2021/02/06");
        FS.add("mySaample3.txt", "/home", "2021/02/04");
        FileSystem newFS = FS.filter("mySam");
        String expected = "[mySample.txt: {Name: mySample.txt, Directory: /home, Modified Date: 2021/02/01}, mySample1.txt: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01}]";
        assertEquals(expected, newFS.outputNameTree().toString());
    }

    @Test
    public void nameFilterTest2() {
        setUp();
        FS.add("mySample1.txt", "/root", "2021/02/01");
        FS.add("mySample.txt", "/home", "2021/02/01");
        FS.add("ySample2.txt", "/user", "2021/02/06");
        FS.add("mySaample3.txt", "/home", "2021/02/04");
        FileSystem newFS = FS.filter("mySam").filter("2021/02/01", "2021/02/06");
        String expected = "[mySample.txt: {Name: mySample.txt, Directory: /home, Modified Date: 2021/02/01}, mySample1.txt: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01}]";;
        assertEquals(expected, newFS.outputNameTree().toString());
    }
}