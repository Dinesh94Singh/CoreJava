package com.timbuchalka;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class Main {

    public static void main(String[] args) {

        try {
            // Pipe is pair of channels - Sink and Source;
            Pipe pipe = Pipe.open();

            // MultiThreaded Environment
            Runnable writer = new Runnable() {
                @Override
                public void run() {
                    try {
                        // Creating a Writable - Sink Channel

                        Pipe.SinkChannel sinkChannel = pipe.sink();
                        ByteBuffer buffer = ByteBuffer.allocate(56);

                        for(int i=0; i<10; i++) {
                            String currentTime = "The time is: " + System.currentTimeMillis();

                            buffer.put(currentTime.getBytes()); // Writing into buffer;
                            buffer.flip(); // Coming to first

                            while(buffer.hasRemaining()) { // if buffer has data
                                sinkChannel.write(buffer); // With Sink, We are wirting buffer
                            }
                            buffer.flip();
                            Thread.sleep(100);

                        }

                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            };


            Runnable reader = new Runnable() {
                @Override
                public void run() {

                    try {
                        Pipe.SourceChannel sourceChannel = pipe.source(); // Readable
                        ByteBuffer buffer = ByteBuffer.allocate(56);

                        for(int i=0; i<10; i++) {
                            // Reading with Source Channel.
                            int bytesRead = sourceChannel.read(buffer); // total size of the written buffer
                             byte[] timeString = new byte[bytesRead]; // New Buffer -
                            buffer.flip(); // Come to First
                            buffer.get(timeString); // Get from Buffer
                            System.out.println("Reader Thread: " + new String(timeString));
                            buffer.flip(); // Go back to buffer start point
                            Thread.sleep(100);
                        }

                    } catch(Exception e) {
                        e.printStackTrace();
                    }

                }
            };

            new Thread(writer).start();
            new Thread(reader).start();

        } catch(IOException e) {
            e.printStackTrace();
        }

//        try (FileOutputStream binFile = new FileOutputStream("data.dat");
//             FileChannel binChannel = binFile.getChannel()) {
//
//            ByteBuffer buffer = ByteBuffer.allocate(100);
//
//
//            byte[] outputBytes = "Hello World!".getBytes();
//            buffer.put(outputBytes);
//            long int1Pos = outputBytes.length;
//            buffer.putInt(245);
//            long int2Pos = int1Pos + Integer.BYTES;
//            buffer.putInt(-98765);
//            byte[] outputBytes2 = "Nice to meet you".getBytes();
//            buffer.put(outputBytes2);
//            long int3Pos = int2Pos + Integer.BYTES + outputBytes2.length;
//            buffer.putInt(1000);
//            buffer.flip();
//
//            binChannel.write(buffer);
//
//
//            RandomAccessFile ra = new RandomAccessFile("data.dat", "rwd");
//            FileChannel channel = ra.getChannel();
//
//            ByteBuffer readBuffer = ByteBuffer.allocate(Integer.BYTES);
//            channel.position(int3Pos);
//            channel.read(readBuffer);
//            readBuffer.flip();
//
//            System.out.println("int3 = " + readBuffer.getInt());
//            readBuffer.flip();
//            channel.position(int2Pos);
//            channel.read(readBuffer);
//            readBuffer.flip();
//
//            System.out.println("int2 = " + readBuffer.getInt());
//            readBuffer.flip();
//            channel.position(int1Pos);
//            channel.read(readBuffer);
//            readBuffer.flip();
//
//            System.out.println("int1 = " + readBuffer.getInt());
//
//            RandomAccessFile copyFile = new RandomAccessFile("datacopy.dat", "rw");
//            FileChannel copyChannel = copyFile.getChannel();
//            channel.position(0);
////            long numTransferred = copyChannel.transferFrom(channel, 0, channel.size());
//            long numTransferred = channel.transferTo(0, channel.size(), copyChannel);
//            System.out.println("Num transferred = " + numTransferred);
//
//            channel.close();
//            ra.close();
//            copyChannel.close();
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
