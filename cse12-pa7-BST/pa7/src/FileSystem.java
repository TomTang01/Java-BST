import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.Scanner;

import javax.swing.RowFilter.Entry;

import org.w3c.dom.Node;


public class FileSystem {

    BST<String, FileData> nameTree;
    BST<String, ArrayList<FileData>> dateTree;
    
    
    public FileSystem() {
        nameTree = new BST<String, FileData>();
        dateTree = new BST<String, ArrayList<FileData>>();
    }


    
    public FileSystem(String inputFile) {
    	// Add your code here
        nameTree = new BST<String, FileData>();
        dateTree = new BST<String, ArrayList<FileData>>();
        try {
            File f = new File(inputFile);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(", ");
                // Add your code here 
                add(data[0],data[1], data[2]);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);

        }
    }


    public void add(String name, String dir, String date) {
        if(name==null || dir==null || date==null) {return;}
        FileData newFile = new FileData(name, dir, date);
    	nameTree.set(name, newFile);
        if(dateTree.containsKey(date)) {
            ArrayList<FileData> fileList = dateTree.get(date);
            for(int i=0; i<fileList.size(); i++) {
                if(fileList.get(i).name.equals(name)) {
                    if(fileList.get(i).lastModifiedDate.compareTo(date)<0) {
                        fileList.get(i).lastModifiedDate = date;
                    }
                    return;
                }
            }
            dateTree.get(date).add(newFile);
            return;
        }
        ArrayList<FileData> newFileList = new ArrayList<>();
        newFileList.add(newFile);
        dateTree.set(date, newFileList);
    }


    public ArrayList<String> findFileNamesByDate(String date) {
        if(date == null) {return null;}
        ArrayList<String> list = new ArrayList<>();
        if(!dateTree.containsKey(date)) {return list;}
        for(int i=0; i<dateTree.get(date).size(); i++) {
            list.add(dateTree.get(date).get(i).name);
        }
        return list;
    }


    public FileSystem filter(String startDate, String endDate) {
        FileSystem newSystem = new FileSystem();
        ArrayList<String> keyList = (ArrayList<String>)(dateTree.keys());
        for(int i=0; i<keyList.size(); i++) {
            if(keyList.get(i).compareTo(startDate)>=0 && keyList.get(i).compareTo(endDate)<0) {
                for(int j=0; j<dateTree.get(keyList.get(i)).size(); j++) {
                    newSystem.add(dateTree.get(keyList.get(i)).get(j).name,
                        dateTree.get(keyList.get(i)).get(j).dir,
                        dateTree.get(keyList.get(i)).get(j).lastModifiedDate);
                }
            }
        }
        return newSystem;
    }
    
    
    public FileSystem filter(String wildCard) {
        FileSystem newSystem = new FileSystem();
        ArrayList<String> keyList = (ArrayList<String>)(nameTree.keys());
        for(int i=0; i<keyList.size(); i++) {
            if(keyList.get(i).contains(wildCard)) {
                newSystem.add(nameTree.get(keyList.get(i)).name,
                nameTree.get(keyList.get(i)).dir,
                nameTree.get(keyList.get(i)).lastModifiedDate);
            }
        }
        return newSystem;
    }
    
    
    public List<String> outputNameTree(){
        List<String> returnList = new ArrayList<>();
        ArrayList<String> nameList = (ArrayList<String>)(nameTree.keys());
        for(int i=0; i<nameList.size(); i++) {
            returnList.add(nameList.get(i) + ": " + nameTree.get(nameList.get(i)).toString());
        }
        return returnList;
    }
    
    
    public List<String> outputDateTree(){
        List<String> returnList = new ArrayList<>();
        ArrayList<String> dateList = (ArrayList<String>)(dateTree.keys());
        for(int i=dateList.size()-1; i>=0; i--) {
            for(int j=dateTree.get(dateList.get(i)).size()-1; j>=0; j--) {
                returnList.add(dateList.get(i) + ": " + dateTree.get(dateList.get(i)).get(j).toString());
            }
        }
        return returnList;
    }
}

