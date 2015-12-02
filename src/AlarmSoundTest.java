/*
 * Copyright (c) 2012, 2014 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import javafx.application.Application;
import javafx.stage.Stage;
import schedule.AlarmThread;
import schedule.Schedule;

import java.io.FileNotFoundException;
import java.util.concurrent.PriorityBlockingQueue;

public class AlarmSoundTest extends Application {


    
    @Override
    public void start(Stage primaryStage) {
        try{
        PriorityBlockingQueue<Schedule.Alarm> priorityQueue = new PriorityBlockingQueue<>();
        AlarmThread alarmThread = new AlarmThread(priorityQueue);
            alarmThread.start();
        }catch(FileNotFoundException alarmSoundNotLoadException){
            alarmSoundNotLoadException.printStackTrace();
        }

        //priorityQueue.add();
        System.out.println("get 1");
        //priorityQueue.add(new Schedule.Alarm(new Date(System.currentTimeMillis()+5000)));
        System.out.println("get 2");
        //priorityQueue.add(new Schedule.Alarm(new Date(System.currentTimeMillis()+15000)));
        System.out.println("get 3");
    }

    /**
     * Java FX Application에서 일반적으로는 main()이 실행되지 않지만, 만약 cmd로 실행한다면,
     * 그렇지 않기 때문에, 아래와 같은 코드를 집어넣는 것이 관례입니다. 지우지 말아주세요.
     *
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
