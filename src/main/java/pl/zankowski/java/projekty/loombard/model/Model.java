package pl.zankowski.java.projekty.loombard.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zano on 2015-03-25.
 */
public class Model {

    public Model() {
       /*dxDiag();
       try {
            crystalDisk();
            batteryTest();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        saveTests();*/
    }

    public void initTest() {
        dxDiag();
        try {
            crystalDisk();
            batteryTest();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        saveTests();
    }

    private void dxDiag() {
        try {
            System.out.println("KEK2E");
            String AbsolutePath = new File("").getAbsolutePath();
            System.out.println(AbsolutePath+"\\dxdiag.txt");
            Process p = new ProcessBuilder("cmd.exe","/c", "dxdiag.exe", "/t", AbsolutePath+"\\dxdiag.txt").start();
            p.waitFor();
            ArrayList<String> dxDiagList;
            try {
                dxDiagList = Reader.read(AbsolutePath+"\\dxdiag.txt");
                String mother = "";
                for(String line : dxDiagList) {
                    if(line.contains("Processor:")&&line.contains("GHz")) {
                        String temp = line.substring(line.lastIndexOf(":")+1);
                        System.out.println(temp);
                        new CPU(temp);
                    } else if(line.contains("Card name:")) {
                        String temp = line.substring(line.lastIndexOf(":") + 1);
                        System.out.println(temp);
                        new GraphicCard(temp);
                    } else if(line.contains("Operating System:")) {
                        String temp = line.substring(line.lastIndexOf(":") + 1);
                        System.out.println(temp);
                        new OS(temp);
                    } else if(line.contains("Memory:")) {
                        System.out.println(line);
                        String temp = line.substring(line.lastIndexOf(":")+1);
                        new Ram("Ram").setSize(temp);
                    } else if(line.contains("System Manufacturer:")) {
                        mother += line.substring(line.lastIndexOf(":")+1);
                    } else if(line.contains("System Model:")) {
                        mother += line.substring(line.lastIndexOf(":")+1);
                        new Motherboard(mother);
                    }
                }
            } catch(FileNotFoundException e) {
                e.printStackTrace();
            } catch(UnsupportedOperationException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void crystalDisk() throws IOException, InterruptedException {
        String AbsolutePath = new File("").getAbsolutePath();
        System.out.println(AbsolutePath+"\\CrystalDiskInfoPortable\\CrystalDiskInfoPortable.exe");
        Process p = new ProcessBuilder("cmd.exe", "/C", AbsolutePath+"\\CrystalDiskInfoPortable\\CrystalDiskInfoPortable.exe", "/CopyExit").start();
        p.waitFor();
        String output = Reader.readBlock(AbsolutePath+"\\CrystalDiskInfoPortable\\App\\CrystalDiskInfo\\DiskInfo.txt");
        String[] temp = output.split("(-{76})\\n.*\\n(-{76})");
        System.out.println(temp.length);
        for(int i = 1; i < temp.length; i++) {
            String[] lines = temp[i].split("\n");
            HDD hdd = new HDD("");
            for(String line : lines) {
                if (line.contains("Model :"))
                    hdd.setModel(line.substring(line.lastIndexOf(":") + 1));
                else if(line.contains("Disk Size :"))
                    hdd.setDiskSize(line.substring(line.lastIndexOf(":")+1));
                else if(line.contains("Hours :"))
                    hdd.setHours(line.substring(line.lastIndexOf(":")+1));
                else if(line.contains("Status :"))
                    hdd.setStatus(line.substring(line.lastIndexOf(":")+1));
            }
            Drives.addDrive(hdd);
        }

    }

    private void batteryTest() throws IOException, InterruptedException {
        String AbsolutePath = new File("").getAbsolutePath();
        Process p = new ProcessBuilder("cmd.exe", "/c", AbsolutePath+"\\BIV\\BatteryInfoView.exe", "/stext",AbsolutePath+"\\BIV\\battery.txt").start();
        p.waitFor();
        String battery = Reader.readBlock(AbsolutePath + "\\BIV\\battery.txt");
        String[] batteryList = battery.split("\n");
        String prv = "";
        String prvprv = "";
        int cap = 0;
        int now = 0;
        for(String line : batteryList) {
            //System.out.println(line);
            if(prv.contains("Obecna pojemność")) {
                line = line.replaceAll("\\D+", "");
                System.out.println(line);
                if(!line.isEmpty()) {
                    cap = Integer.parseInt(line);
                    Battery bat = new Battery((float)cap);
                } else {
                    Battery bat = new Battery(0);
                }

            }
            prv = line;
        }
    }

    private void saveTests() {
        String AbsolutePath = new File("").getAbsolutePath();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date date = new Date();
        ComputerParts cp = new ComputerParts("");
        HashMap<String, ComputerParts> list = cp.getList();
        String line = list.get("Model").getName();
        //line = line.replaceAll(".", "");
        //line = line.replaceAll(",", "");
        String path = AbsolutePath+"\\Output\\"+ dateFormat.format(date)+" - "+line;
        Boolean state = new File(path).mkdir();
        System.out.print(state+" KURWA ");
        try {
            copyFile(new File(AbsolutePath + "\\BIV\\battery.txt"), new File(path+ "\\battery.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            copyFile(new File(AbsolutePath+"\\CrystalDiskInfoPortable\\App\\CrystalDiskInfo\\DiskInfo.txt"), new File(path+"\\DiskInfo.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            copyFile(new File(AbsolutePath + "\\dxdiag.txt"), new File(path+ "\\dxdiag.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyFile( File from, File to ) throws IOException {
        Files.copy(from.toPath(), to.toPath());
    }

}
