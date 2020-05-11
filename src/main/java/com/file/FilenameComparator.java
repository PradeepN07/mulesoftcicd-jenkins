package com.file;

import java.io.File;
import java.util.Comparator;

public  class FilenameComparator implements Comparator<Object> {

    public int compare(Object o1, Object o2) {
        File file1 = (File) o1;
        File file2 = (File) o2;
        int index1 = Integer.parseInt(file1.getName().substring(2, file1.getName().indexOf(".")));
        int index2 = Integer.parseInt(file2.getName().substring(2, file2.getName().indexOf(".")));
        if(index1==index2){
        	return 0;
        }else if(index1<index2){
        	return -1;
        }else 
        	return 1;
    }

	
}