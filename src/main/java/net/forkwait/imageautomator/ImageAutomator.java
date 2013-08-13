/**
 * Copyright 2013 Alessandro Miliucci <lifeisfoo@gmail.com>
 * This file is part of ImageAutomator <https://github.com/lifeisfoo/ImageAutomator>
 * -
 * ImageAutomator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * -
 * ImageAutomator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * -
 * You should have received a copy of the GNU General Public License
 * along with ImageAutomator. If not, see <http://www.gnu.org/licenses/>.
 */

package net.forkwait.imageautomator;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.*;
import net.coobird.thumbnailator.*;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;

import javax.imageio.ImageIO;

public class ImageAutomator {
    public static void main(String[] args) throws IOException{
        String inputImage = "";

        Options options = new Options();
        options.addOption("o", true, "output file name (e.g. thumb.jpg), default thumbnail.filename.ext");
        options.addOption("q", true, "jpeg quality (e.g. 0.9, max 1.0), default 0.97");
        options.addOption("s", true, "output max side length in px (e.g. 800), default 1200");
        options.addOption("w", true, "watermark image file");
        options.addOption("wt", true, "watermark transparency (e.g. 0.5, max 1.0), default 1.0");
        options.addOption("wp", true, "watermark position (e.g. 0.9, max 1.0), default BOTTOM_RIGHT");

        /*
        TOP_LEFT
        TOP_CENTER
        TOP_RIGHT
        CENTER_LEFT
        CENTER
        CENTER_RIGHT
        BOTTOM_LEFT
        BOTTOM_CENTER
        BOTTOM_RIGHT
         */


        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse( options, args);
            if(cmd.getArgs().length < 1) {
                throw new ParseException("Too few arguments");
            }else if(cmd.getArgs().length > 1){
                throw new ParseException("Too many arguments");
            }
            inputImage = cmd.getArgs()[0];
        } catch (ParseException e) {
            showHelp(options, e.getLocalizedMessage());
            System.exit(-1);
        }

        Thumbnails.Builder<File> st = Thumbnails.of(inputImage);


        if(cmd.hasOption("q")){
            st.outputQuality(Double.parseDouble(cmd.getOptionValue("q")));
        }else{
            st.outputQuality(0.97f);
        }

        if(cmd.hasOption("s")){
            st.size(
                    Integer.parseInt(cmd.getOptionValue("s")),
                    Integer.parseInt(cmd.getOptionValue("s")));
        }else{
            st.size(1200, 1200);
        }
        if(cmd.hasOption("w")){
            Positions position = Positions.BOTTOM_RIGHT;
            float trans = 0.5f;
            if(cmd.hasOption("wp")){
                position = Positions.valueOf(cmd.getOptionValue("wp"));
            }
            if(cmd.hasOption("wt")){
               trans = Float.parseFloat(cmd.getOptionValue("wt"));
            }

            st.watermark(position,
                    ImageIO.read(new File(cmd.getOptionValue("w"))),
                    trans);
        }
        if(cmd.hasOption("o")){
            st.toFile(new File(cmd.getOptionValue("o")));
        }else{
            st.toFiles(Rename.PREFIX_DOT_THUMBNAIL);
        }

        //.outputFormat("jpg")
        System.exit(0);
    }

   private static void showHelp(Options options, String errMessage){
       if(errMessage != null){
           System.out.println("Error: "+errMessage);
       }
       HelpFormatter formatter = new HelpFormatter();
       formatter.printHelp("ImageAutomator [options] <inputImage>", options);
   }
}
