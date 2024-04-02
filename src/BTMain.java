import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;

public class BTMain {

    public static void main(String[] args) {

        System.out.println("Hello and welcome to BT Technical Challenge!");
        Util utilObj = new Util();
        // Parse input.txt and all data map it to POJO
        try (Scanner scanner = new Scanner(new File(args[0])).useDelimiter("\\n")) {
            String[] data;
            Login[] logins = new Login[0];
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                data = utilObj.patternMatcher(line);
                if(!Objects.isNull(data) && data.length == 3) {
                    String logTime = data[0];
                    String userId = data[1];
                    Status status = null;
                    try {
                        status = Status.valueOf(data[2].toUpperCase());
                    } catch (IllegalArgumentException e){
                        System.out.println("Data is not valid : "+line);
                        continue;
                    }

                    Login newLogin = new Login(userId, logTime, status);
                    logins = addLogin(logins, newLogin);
                } else {
                    System.out.println("Data is not valid : "+line);
                }
            }
            System.out.println("Total valid input data count is : "+logins.length);
            System.out.println("=============================================");
            processLoginsData(logins, utilObj);
        } catch (FileNotFoundException | ParseException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static void processLoginsData(Login[] logins, Util utilObj) throws ParseException {
        //as it is a FIFO
        Deque<Login> deque = new LinkedList<>();
        Map<String, List<Long>> result = new HashMap<String, List<Long>>();
        for (Login login : logins) {
            if (login.getStatus().compareTo(Status.START) == 0) {
                deque.addFirst(login);
            } else {
                //if deque has the same key
                if (!deque.isEmpty()
                        && (deque.peekFirst().equals(login))) {
                    Login popLog = deque.removeFirst();
                    populateResultMap(utilObj, login, result, popLog);
                } else {
                    //if no log for start for the user but has only end logging
                    if (login.getStatus().compareTo(Status.END) == 0) populateResultMap(utilObj, login, result, logins[0]);
                    else populateResultMap(utilObj, logins[logins.length - 1], result, login);
                }
            }
        }
        //if no log for end for the user but has only start logging
        while(!deque.isEmpty()) {
            Login popLog = deque.removeFirst();
            populateResultMap(utilObj, popLog, result, logins[logins.length - 1]);
        }
        for(Map.Entry<String, List<Long>> entry : result.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().size() + " " +
                    entry.getValue().stream().mapToLong(Long::longValue).sum());
        }
        System.out.println("=============================================");
    }

    /* Store the data in Map. UserID as key and a List of logins in seconds*/
    private static void populateResultMap(Util utilObj, Login login, Map<String, List<Long>> result, Login popLog) throws ParseException {
        List<Long> secs;
        secs = (Objects.isNull(result.get(login.getUserId()))) ? new ArrayList<Long>() : result.get(login.getUserId());
        secs.add(utilObj.calculateSeconds(popLog, login));
        result.put(login.getUserId(), secs);
    }

    private static Login[] addLogin(Login[] logins, Login loginToAdd) {
        Login[] newLogins = new Login[logins.length + 1];
        System.arraycopy(logins, 0, newLogins, 0, logins.length);
        newLogins[newLogins.length - 1] = loginToAdd;
        return newLogins;
    }
}