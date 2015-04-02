package pl.zankowski.java.projekty.loombard.model;

import com.sun.jna.platform.win32.Advapi32Util;
import pl.zankowski.java.projekty.loombard.utilities.Kernel32;
import pl.zankowski.java.projekty.loombard.utilities.jWMI;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

/**
 * Created by Zano on 2015-04-01.
 */
public class SystemInfoReader {

    public static void main(String[] args) {
        SystemInfoReader sir = new SystemInfoReader();
        try {
            System.out.println(sir.getMemory());
            sir.getMemorys();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long getMemory() throws Exception {
        Long[] memorys = getMemorys();
        long totalSize = 0;
        for(Long nmb : memorys)
            totalSize = totalSize + nmb;
        return totalSize;
    }

    public Long[] getMemorys() throws Exception {
        String[] memorys = jWMI.getWMIValue("Select * from Win32_PhysicalMemory", "Capacity").trim().split("\n");
        Long[] nmb = new Long[memorys.length];
        for(int i = 0; i < memorys.length; i++)
            nmb[i] = Long.parseLong(memorys[i].trim())/1024/1024;
        return nmb;
    }

    public int getBatteryUsage() throws Exception {
        return Integer.parseInt(getBatteryFullChargeCapacity())/Integer.parseInt(getBatteryDesignCapacity());
    }

    public String getBatteryDesignCapacity() throws Exception {
        return jWMI.getWMIValue("Select * from Win32_Battery", "DesignCapacity");
    }

    public String getBatteryFullChargeCapacity() throws Exception {
        return jWMI.getWMIValue("Select * from Win32_Battery", "FullChargeCapacity");
    }

    public String getVideoController() throws Exception {
        return jWMI.getWMIValue("Select * from Win32_VideoController", "Name");
    }

    public String[] getVideoControllers() throws Exception {
        return getVideoController().split("\n");
    }

    public String getSystemArchitecture() {
        String arch = Advapi32Util.registryGetStringValue
                (HKEY_LOCAL_MACHINE,
                        "SYSTEM\\CurrentControlSet\\Control\\Session Manager\\Environment",
                        "PROCESSOR_ARCHITECTURE");
        switch (arch) {
            case ("AMD64"): return "64-bit";
            case ("IA64"): return "64-bit";
            case ("x86"): return "32-bit";
            default: return "";
        }
    }

    public String getProcessorName() {
        String processor = Advapi32Util.registryGetStringValue
                (HKEY_LOCAL_MACHINE,
                        "HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0\\",
                        "ProcessorNameString");
        return processor;
    }

    public String getSystem() {
        String os = Advapi32Util.registryGetStringValue
                (HKEY_LOCAL_MACHINE,
                        "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion",
                        "ProductName");
        String build = Advapi32Util.registryGetStringValue
                (HKEY_LOCAL_MACHINE,
                        "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion",
                        "BuildLab");
        return os+" "+getSystemArchitecture()+" "+build;
    }

    public String getModel() {
        String manufact = Advapi32Util.registryGetStringValue
                (HKEY_LOCAL_MACHINE,
                        "SYSTEM\\HardwareConfig\\Current\\",
                        "SystemManufacturer");
        String name = Advapi32Util.registryGetStringValue
                (HKEY_LOCAL_MACHINE,
                        "SYSTEM\\HardwareConfig\\Current\\",
                        "SystemProductName");
        return manufact+" "+name;
    }

    public String getComputerName() {
        return Advapi32Util.registryGetStringValue
                (HKEY_LOCAL_MACHINE,
                        "SYSTEM\\CurrentControlSet\\Control\\ComputerName\\ComputerName",
                        "ComputerName");
    }

}
