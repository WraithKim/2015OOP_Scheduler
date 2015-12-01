import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by Donghwan on 12/1/2015.
 */
public class Test {

    public static void main(String[] args){
        // TODO Auto-generated method stub
        /*
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
        */

        /*
        ExecutorService test2 = Executors.newFixedThreadPool(1, (r) -> new Thread(r));
        test2.execute(()->{
            System.out.println("hey");
            try{
                Thread.sleep(5000);
                test2.execute(()->{
                    System.out.println("hey");
                    try{
                        Thread.sleep(5000);
                    }catch(InterruptedException ie){
                    }
                });
            }catch(InterruptedException ie){
            }
        });

        test2.execute(()->{System.out.println("wow"); test2.shutdown();});
        System.out.println("end");
        /*
        ExecutorService test = Executors.newFixedThreadPool(1, (Runnable r) -> {
            return new Thread(()->{
                    System.out.println("hey");
            });
        });
        test.execute(()->{});
        */
    }

}
