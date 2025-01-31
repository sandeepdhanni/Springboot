package com.example.ReadingPath;

import com.hierynomus.msfscc.fileinformation.FileIdBothDirectoryInformation;
import com.hierynomus.mssmb2.SMBApiException;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.share.DiskShare;
import com.hierynomus.smbj.share.Share;
import com.hierynomus.smbj.utils.SmbFiles;
import com.hierynomus.smbj.common.SMBRuntimeException;

import java.io.IOException;
import java.util.List;

public class SMBClientExample {
    public static void main(String[] args) {
        String hostname = "10.0.0.157";
        String share = "sandeep";
        String folderPath = "\\\\WIN10-DEV01\\Users\\Sreenivas Bandaru\\Desktop\\sandeep";
        String username = "DOMAIN\\\\Shreya Suddala";
        String password = "sarasu10";
        // Build the SMBClient and authentication context
        SMBClient client = new SMBClient();
        AuthenticationContext context = new AuthenticationContext(username, password.toCharArray(), null);

        try (Connection connection = client.connect(hostname)) {
            // Authenticate and connect to the share on the server
            Share smbShare = connection.authenticate(context).connectShare(share);

            // Ensure it's a DiskShare, which allows file listing
            if (smbShare instanceof DiskShare) {
                DiskShare diskShare = (DiskShare) smbShare;

                // List files in the folder
                String folder = "/" + folderPath;
                for (FileIdBothDirectoryInformation file : diskShare.list(folder)) {
                    System.out.println(file.getFileName());
                }
            } else {
                System.out.println("The share is not a DiskShare.");
            }
        } catch (IOException | SMBApiException e) {
            e.printStackTrace();
        }
    }
}

