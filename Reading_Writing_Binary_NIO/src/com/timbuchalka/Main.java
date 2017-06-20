package com.timbuchalka;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Main {

    public static void main(String[] args) {

	    try(FileOutputStream binFile = new FileOutputStream("data.dat");
            FileChannel binChannel = binFile.getChannel()) {

            byte[] outputBytes = "Hello World!".getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(outputBytes);
            int numBytes = binChannel.write(buffer);
            System.out.println("numBytes written was: " + numBytes);

            // 2nd Way

            ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES); // specify the size
            intBuffer.putInt(245); // add to buffer. Once added, the FilePointer() is pointing at end
            intBuffer.flip(); // Get it back to first Position
            numBytes = binChannel.write(intBuffer); // Write into File
            System.out.println("numBytes written was: " + numBytes); // While Writing, it will specify the number of bytes written

            intBuffer.flip(); // Go back to First Position
            intBuffer.putInt(-98765); // Add to Buffer
            intBuffer.flip(); // Buffer is pointing at end, go back to first to make a write
            numBytes = binChannel.write(intBuffer); // From first, write it to file
            System.out.println("numBytes written was: " + numBytes);

            RandomAccessFile ra = new RandomAccessFile("data.dat", "rwd");
            FileChannel channel = ra.getChannel();
            outputBytes[0] = 'a';
            outputBytes[1] = 'b';
            buffer.flip(); // If Not Specified, OUTPUT: ablloworld, because
            long numBytesRead = channel.read(buffer);
            if(buffer.hasArray()) {
                System.out.println("byte buffer = " + new String(buffer.array()));
            }

            // Absolute read

                intBuffer.flip(); // Make Buffer point at Beginning
                numBytesRead = channel.read(intBuffer); //
                System.out.println(intBuffer.getInt(0)); // No need to go back to First, Index: 0 does it for us
                intBuffer.flip(); // Since Buffer is Cleared, Go back to First Again
                numBytesRead = channel.read(intBuffer); // Specify to read from File
                System.out.println(intBuffer.getInt(0));


            /* Relative read

                intBuffer.flip(); // Get the Buffer to Starting
                numBytesRead = channel.read(intBuffer); // Initialize the Buffer from Channel, but it is pointing at end
                intBuffer.flip(); // Since pointing at end, Get back to First
                System.out.println(intBuffer.getInt()); // Get First Value again, 1 Buffer Cleared and the Pointer not pointing to start
                intBuffer.flip(); // Come back to first pos
                numBytesRead = channel.read(intBuffer); // Specify that we are reading from Channel
                intBuffer.flip(); // Again come back to first pos
                System.out.println(intBuffer.getInt()); // Get 2nd Value - Buffer Cleared, nothing there in file.
            */
            channel.close();
            ra.close();

//            System.out.println("outputBytes = " + new String(outputBytes));

//            RandomAccessFile ra = new RandomAccessFile("data.dat", "rwd");
//            byte[] b = new byte[outputBytes.length];
//            ra.read(b);
//            System.out.println(new String(b));
//
//            long int1 = ra.readInt();
//            long int2 = ra.readInt();
//            System.out.println(int1);
//            System.out.println(int2);

        } catch(IOException e) {
	        e.printStackTrace();
        }
    }
}
