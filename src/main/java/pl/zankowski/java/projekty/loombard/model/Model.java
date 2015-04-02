package pl.zankowski.java.projekty.loombard.model;

import pl.zankowski.java.projekty.loombard.model.OO.*;
import pl.zankowski.java.projekty.loombard.model.OO.SystemOS;
import pl.zankowski.java.projekty.loombard.utilities.Reader;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Zano on 2015-03-25.
 */
public class Model {

    private SystemInfoReader sir = new SystemInfoReader();
    public PC pc;

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
        checkComputerSpecification();
        try {
            crystalDisk();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        saveTests();
    }

    /*private void dxDiag() {
        try {
            System.out.println("KEK2E");
            String AbsolutePath = new File("").getAbsolutePath();
            System.out.println(AbsolutePath+"\\dxdiag.txt");
            Process p = new ProcessBuilder("cmd.exe","/c", "dxdiag.exe", "/t", AbsolutePath+"\\dxdiag.txt").start();
            p.waitFor();
            ArrayList<String> dxDiagList;
            try {
                dxDiagList = Reader.read(AbsolutePath + "\\dxdiag.txt");
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
    }*/

    private void crystalDisk() throws IOException, InterruptedException {
        String AbsolutePath = new File("").getAbsolutePath();
        System.out.println(AbsolutePath+"\\CrystalDiskInfoPortable\\CrystalDiskInfoPortable.exe");
        Process p = new ProcessBuilder("cmd.exe", "/C", AbsolutePath+"\\CrystalDiskInfoPortable\\CrystalDiskInfoPortable.exe", "/CopyExit").start();
        p.waitFor();
        String output = Reader.readBlock(AbsolutePath+"\\CrystalDiskInfoPortable\\App\\CrystalDiskInfo\\DiskInfo.txt");
        processCrystalDiskOutput(output);
    }

    private void processCrystalDiskOutput(String output) {
        String[] temp = output.split("(-{76})\\n.*\\n(-{76})");
        System.out.println(temp.length);
        for(int i = 1; i < temp.length; i++) {
            String[] lines = temp[i].split("\n");
            Drive hdd = new Drive("");
            for(String line : lines) {
                if (line.contains("Model :"))
                    hdd.setName(line.substring(line.lastIndexOf(":") + 1));
                else if(line.contains("Disk Size :"))
                    hdd.setSize(line.substring(line.lastIndexOf(":")+1));
                else if(line.contains("Hours :"))
                    hdd.setHours(line.substring(line.lastIndexOf(":")+1));
                else if(line.contains("Status :"))
                    hdd.setStatus(line.substring(line.lastIndexOf(":")+1));
            }
            pc.addDrive(hdd);
        }
    }

    /*private void batteryTest() throws IOException, InterruptedException {
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
    }*/

    private void checkComputerSpecification() {
        SystemOS systemOS = new SystemOS(sir.getSystem());
        Processor processor = new Processor(sir.getProcessorName());
        RAM ram;
        Battery battery;
        try {
            battery = new Battery("Battery", sir.getBatteryUsage());
        } catch (Exception e) {
            battery = new Battery("Battery", 0);
        }
        try {
            ram = new RAM("RAM", sir.getMemory());
        } catch (Exception e) {
            ram = new RAM("RAM", 0l);
        }
        pc = new PC(sir.getModel(), systemOS, ram, processor, battery);
        try {
            for(String name : sir.getVideoControllers()) {
                GPU gpu = new GPU(name);
                pc.addGPUS(gpu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveTests() {
        String AbsolutePath = new File("").getAbsolutePath();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date date = new Date();
        String line = pc.getModel();
        String fileName =  dateFormat.format(date)+" - "+line;
        String path = AbsolutePath+"\\Output\\"+ fileName;
        Boolean state = new File(path).mkdir();
        try {
            copyFile(new File(AbsolutePath+"\\CrystalDiskInfoPortable\\App\\CrystalDiskInfo\\DiskInfo.txt"),
                    new File(path+"\\DiskInfo.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeOutput(createOutput(),  dateFormat.format(date)+" - "+line);
    }

    public void readOutput(File file) throws FileNotFoundException, UnsupportedEncodingException {
        ArrayList<String> list = Reader.read(file.getAbsolutePath());
        pc = new PC();
        for(int i = 0; i < list.size(); i++) {
            switch (i) {
                case (0): pc.setModel(list.get(i));
                    break;
                case (1): pc.setSystemOS(new SystemOS(list.get(i)));
                    break;
                case (2): pc.setProcessor(new Processor(list.get(i)));
                    break;
                case (3): pc.setRam(new RAM("RAM", Long.parseLong(list.get(i))));
                    break;
                case (4):
                    String[] temp = list.get(i).split(", ");
                    for(int j = 0; j < temp.length-1; j++)
                        pc.addGPUS(new GPU(temp[j]));
                    break;
                case (5): pc.setBattery(new Battery("Battery", Float.parseFloat(list.get(i))));
                    break;
                default:
                    break;
            }
        }
        System.out.println(file.getAbsoluteFile().getParentFile().getAbsolutePath() + "\\DiskInfo.txt");
        processCrystalDiskOutput(Reader.readBlock(file.getAbsoluteFile().getParentFile().getAbsolutePath()+"\\DiskInfo.txt"));
    }

    private void writeOutput(String output, String fileName) {
        String AbsolutePath = new File("").getAbsolutePath();
        try {
            BufferedWriter buff = new BufferedWriter(new FileWriter(AbsolutePath+"\\Output\\"+fileName+"\\"+"specs.out"));
            try {
                buff.write(output);
            } finally {
                buff.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private String createOutput() {
        StringBuilder sb = new StringBuilder();
        sb.append(pc.getModel() + "\n");
        sb.append(pc.getSystemOS().getName() + "\n");
        sb.append(pc.getProcessor().getName() + "\n");
        sb.append(pc.getRam().getSize() + "\n");
        for(GPU gpu : pc.getGpus())
            sb.append(gpu.getName().trim()+", ");
        sb.append("\n"+pc.getBattery().getUsage());
        return sb.toString();
    }

    public static void copyFile( File from, File to ) throws IOException {
        Files.copy(from.toPath(), to.toPath());
    }

}
