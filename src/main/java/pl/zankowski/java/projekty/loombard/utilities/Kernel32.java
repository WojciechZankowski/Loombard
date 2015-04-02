package pl.zankowski.java.projekty.loombard.utilities;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Zano on 2015-03-29.
 */
public interface Kernel32 extends StdCallLibrary {

    public Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("Kernel32", Kernel32.class);

    /**
     * @see http://msdn2.microsoft.com/en-us/library/aa373232.aspx
     */
    class SYSTEM_POWER_STATUS extends Structure {
        public byte ACLineStatus;
        public byte BatteryFlag;
        public byte BatteryLifePercent;
        public byte Reserved1;
        public int BatteryLifeTime;
        public int BatteryFullLifeTime;

        @Override
        protected List<String> getFieldOrder() {
            ArrayList<String> fields = new ArrayList<String>();
            fields.add("ACLineStatus");
            fields.add("BatteryFlag");
            fields.add("BatteryFullLifeTime");
            fields.add("BatteryLifePercent");
            fields.add("BatteryLifeTime");
            fields.add("Reserved1");
            return fields;
        }

        /**
         * The AC power status
         */
        public String getACLineStatusString() {
            switch (ACLineStatus) {
                case (0): return "Offline";
                case (1): return "Online";
                default: return "Unknown";
            }
        }

        /**
         * The battery charge status
         */
        public String getBatteryFlagString() {
            switch (BatteryFlag) {
                case (1): return "High, more than 66 percent";
                case (2): return "Low, less than 33 percent";
                case (4): return "Critical, less than five percent";
                case (8): return "Charging";
                case ((byte) 128): return "No system battery";
                default: return "Unknown";
            }
        }

        /**
         * The percentage of full battery charge remaining
         */
        public String getBatteryLifePercent() {
            return (BatteryLifePercent == (byte) 255) ? "Unknown" : BatteryLifePercent + "%";
        }

        /**
         * The number of seconds of battery life remaining
         */
        public String getBatteryLifeTime() {
            return (BatteryLifeTime == -1) ? "Unknown" : BatteryLifeTime + " seconds";
        }

        /**
         * The number of seconds of battery life when at full charge
         */
        public String getBatteryFullLifeTime() {
            return (BatteryFullLifeTime == -1) ? "Unknown" : BatteryFullLifeTime + " seconds";
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ACLineStatus: " + getACLineStatusString() + "\n");
            sb.append("Battery Flag: " + getBatteryFlagString() + "\n");
            sb.append("Battery Life: " + getBatteryLifePercent() + "\n");
            sb.append("Battery Left: " + getBatteryLifeTime() + "\n");
            sb.append("Battery Full: " + getBatteryFullLifeTime() + "\n");
            return sb.toString();
        }
    }

    class MEMORYSTATUS extends Structure {

        public int dwLength;
        public int dwMemoryLoad;
        public long dwTotalPhys;
        public long dwAvailPhys;
        public long dwTotalPageFile;
        public long dwAvailPageFile;
        public long dwTotalVirtual;
        public long dwAvailVirtual;

        @Override
        protected List getFieldOrder() {
            ArrayList<String> fields = new ArrayList<String>();
            fields.add("dwLength");
            fields.add("dwMemoryLoad");
            fields.add("dwTotalPhys");
            fields.add("dwAvailPhys");
            fields.add("dwTotalPageFile");
            fields.add("dwAvailPageFile");
            fields.add("dwTotalVirtual");
            fields.add("dwAvailVirtual");
            return fields;
        }

        public String getTotalPhysString() {
            return Math.round((double)dwTotalPhys/(1024*1024*1000)) + "GB RAM";
        }

        public String getMemoryLoadString() {
            return dwMemoryLoad+"";
        }

        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append(dwLength + "\n");
            sb.append(getMemoryLoadString() + "\n");
            sb.append(getTotalPhysString() + "\n");
            sb.append(dwAvailPhys + "\n");
            sb.append(dwTotalPageFile + "\n");
            sb.append(dwAvailPageFile + "\n");
            sb.append(dwTotalVirtual + "\n");
            sb.append(dwAvailVirtual + "\n");
            return sb.toString();
        }
    }

    /**
     * Fill the structure.
     */
    public int GetSystemPowerStatus(SYSTEM_POWER_STATUS result);

    public void GlobalMemoryStatus(MEMORYSTATUS result);

}
