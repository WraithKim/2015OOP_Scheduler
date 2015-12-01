import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Donghwan on 12/1/2015.
 */
public class Test {

    public static void main(String[] args){
        // TODO Auto-generated method stub
        try{
            Player p;
            p = new Player(new FileInputStream("."+ File.separator + "res" + File.separator + "DingDong.mp3"));
            p.play();
            p.close();
        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }catch(JavaLayerException jle){
            jle.printStackTrace();
        }
    }

}
