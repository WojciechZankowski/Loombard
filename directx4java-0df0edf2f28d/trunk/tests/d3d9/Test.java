package d3d9;

import org.bridj.Pointer;

/**
 * Created by Zano on 2015-04-02.
 */
public class Test {

    public static void main(String[] args) {
        D3DADAPTER_IDENTIFIER9 d3 = new D3DADAPTER_IDENTIFIER9();

        System.out.println(d3.DeviceName().getString(Pointer.StringType.C));
    }

}
