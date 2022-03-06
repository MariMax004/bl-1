package ru.mariamaximova.bl1.application.auth.xml;

import com.thoughtworks.xstream.XStream;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import ru.mariamaximova.bl1.application.auth.domain.TokenRepository;
import ru.mariamaximova.bl1.application.auth.model.UserXmlDto;
import ru.mariamaximova.bl1.application.auth.model.UserXmlListDto;
import ru.mariamaximova.bl1.application.customer.domain.Customer;
import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserXmlRepository {

    protected final TokenRepository tokenRepository;


    public UserXmlDto getCustomerByEmail(String email) {
        XStream xstream = new XStream();
        Class<?>[] classes = new Class[] { UserXmlDto.class, UserXmlListDto.class};
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);
        xstream.alias("user", UserXmlDto.class);
        xstream.alias("users", UserXmlListDto.class);
        xstream.addImplicitCollection(UserXmlListDto.class, "list");

        UserXmlListDto list = (UserXmlListDto) xstream.fromXML(readFromFile());
        System.out.println(list.get().size());
        List<UserXmlDto> filteredList = list.get().stream().filter(u -> u.getEmail().equals(email)).collect(Collectors.toList());
        UserXmlDto user = new UserXmlDto();
        if (!filteredList.isEmpty()) user = filteredList.get(0);
        return user;
    }

    public void save(Customer customer) {
        XStream xstream = new XStream();
        Class<?>[] classes = new Class[] { UserXmlDto.class, UserXmlListDto.class};
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);
        xstream.alias("user", UserXmlDto.class);
        xstream.alias("users", UserXmlListDto.class);
        xstream.addImplicitCollection(UserXmlListDto.class, "list");

        UserXmlDto user = new UserXmlDto(
//                set maxId
            customer.getId(),
            customer.getEmail(),
            customer.getPassword(),
            customer.getFirstName(),
            customer.getLastName(),
            false
        );
        String xmlUser = xstream.toXML(user);
        writeToFile(xmlUser);
    }


    private String readFromFile(){
        String data = "";
        try {
            File file = new ClassPathResource("user.xml").getFile();
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                data += "\n";
                data += myReader.nextLine();
            }
            myReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void writeToFile(String line) {
        line += "\n</users>";
        try {
            // deleting last line of file
            File file = new ClassPathResource("user.xml").getFile();
            System.out.println(file.getAbsolutePath());
            RandomAccessFile f = new RandomAccessFile(file, "rw");
            long length = f.length() - 1;
            byte b;
            do {
                length -= 1;
                f.seek(length);
                b = f.readByte();
            } while (b != 10);
            f.setLength(length + 1);
            f.close();

            // writing user to the end of file
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(line);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
