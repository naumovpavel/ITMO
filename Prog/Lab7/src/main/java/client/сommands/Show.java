package client.сommands;

import common.Commands;
import common.models.Model;
import common.network.Status;
import common.network.Request;
import common.network.Response;

import java.util.Scanner;

/**
 * Show command
 */
public class Show extends Command {

    public Show() {
        super("show", "Выводит все элементы коллекции");
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        Request request = new Request(Commands.SHOW);
        Response response = client.sendAndReceive(request);

        if(response.getStatus() != Status.OK) {
            System.out.println(response.getError());
            return;
        }

        if(response.get("collection") == null) {
            System.out.println(response.getAnswer());
            return;
        }

        Object[] collection = response.get("collection");
        int m = (collection.length + 9) / 10;
        Scanner scanner = new Scanner(System.in);
        byte[][] commands = new byte[2][];
        commands[0] = new byte[]{27, 91, 65};
        commands[1] = new byte[]{27, 91, 66};

        for (int i = 0; i < m; i++) {
            String command = scanner.nextLine().strip();
            if(command.equals("")) {
                break;
            }
            if(command.equals(new String(commands[1]))) {
                for(int j = 0; j < 10; j++) {
                    System.out.println(collection[i*10 + j]);
                }
            } else if(command.equals(new String(commands[0]))) {
                if(i > 1) {
                    i = i-2;
                } else {
                    i = -1;
                    continue;
                }
                for(int j = 0; j < 10; j++) {
                    System.out.println(collection[i*10 + j]);
                }
            } else {
                i--;
                continue;
            }
            System.out.printf("[%d / %d]\n", i+1, m);
        }

    }
}

