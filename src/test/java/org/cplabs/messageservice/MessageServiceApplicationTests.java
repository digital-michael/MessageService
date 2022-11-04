package org.cplabs.messageservice;

import org.cplabs.messageservice.messaging.MessageManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MessageServiceApplicationTests {

    @Autowired
    MessageManager manager;

    @Test
    void contextLoads() {
    }


    @Test()
    void test_manager() {

        var names = List.of( "Mike", "Kalina", "Maggy");

        Assertions.assertNotNull( manager );

        // confirm no messages
        names.forEach( n -> Assertions.assertEquals( 0, manager.getMessageFor(n).size(), "Expected empty list!" ));

    }

    @Test
    void test_manager_send_messages_direct() {

        var names = List.of( "Mike", "Kalina", "Maggy");

        Assertions.assertNotNull( manager );

        var n = 3;

        // confirm no messages
        String source;
        for (int i = 0; i < n; ++i) {
            var target = names.get(names.size()-1);
            var iter = names.iterator();
            while (iter.hasNext()) {
                source = iter.next();
                manager.recordNewMessageFor(source, target, String.format("%o: %s to %s", n*i, source, target));
                target = source;
            }
        }

        // NOTE: each message "from to" for the recipient has a matching "to from" for the sender
        // confirm N+names.size() messages
        Assertions.assertEquals( (names.size()*n)*2, manager.getAllMessages().size(), "Didn't get all the messages expected!");

        // confirm N messages each
        names.forEach( name -> Assertions.assertEquals( (n*2), manager.getMessageFor(name).size(), "Expected not empty list for " + name ));

    }

    @Test
    void test_manager_send_messages_channel() {

        var names = List.of( "Mike", "Kalina", "Maggy");
        var channels = List.of( "Public", "Games", "Hobbies" );

        Assertions.assertNotNull( manager );

        var n = 3;

        // confirm no messages
        String source;
        for (int i = 0; i < n; ++i) {
            var target = names.get(names.size()-1);
            var nameIter = names.iterator();
            while (nameIter.hasNext()) {
                source = nameIter.next();
                var channelIter = channels.iterator();
                while( channelIter.hasNext() ) {
                    var channel = channelIter.next();
                    manager.recordNewMessageFor(channel, source, target, String.format("%o: (%S) %s to %s", n * i, channel, source, target));
                }
                target = source;
            }
        }

        // NOTE: each message "from to" for the recipient has a matching "to from" for the sender
        // NOTE: each message is send to each channel
        // confirm (N+names.size())*number of channels (3) messages
        Assertions.assertEquals( (names.size()*n)*channels.size(), manager.getAllMessages().size(), "Didn't get all the messages expected!");

        // confirm N messages each
        names.forEach( name -> Assertions.assertEquals( (n*channels.size()), manager.getMessageFor(name).size(), "Expected not empty list for " + name ));

        // confirm message per channel
        channels.forEach( c -> System.out.println( c + ": " + manager.getMessageForChannel(c).size() ) );
    }
}

