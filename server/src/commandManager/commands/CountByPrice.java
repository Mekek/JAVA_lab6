package commandManager.commands;

import models.Ticket;
import models.handlers.TicketHandler_;
import models.handlers.CollectionHandler_;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import responses.CommandStatusResponse_;

import java.util.Objects;
import java.util.Vector;
import java.util.stream.Stream;

import static java.lang.Float.parseFloat;

public class CountByPrice implements Command {
    private static final Logger logger = LogManager.getLogger("io.github.Mekek.lab6.commands.countByPrice");
    private CommandStatusResponse_ response;

    @Override
    public String getName() {
        return "count_by_price";
    }

    @Override
    public String getDescr() {
        return "Prints number of elements in collection with the same price.";
    }

    @Override
    public String getArgs() {
        return "price";
    }


    @Override
    public void execute(String[] args) {

        CollectionHandler_<Vector<Ticket>, Ticket> collectionHandler = TicketHandler_.getInstance();

        StringBuilder sb = new StringBuilder();

        Stream<Ticket> stream = collectionHandler.getCollection().stream();
        long count = stream.filter(ticket -> Objects.equals(ticket.getPrice(), Float.valueOf(args[1]))).count();


        sb.append(count);
        response = CommandStatusResponse_.ofString(sb.toString());

        if (collectionHandler.getCollection().isEmpty())
            response = CommandStatusResponse_.ofString("There's nothing to show...");

        logger.info(response.getResponse());
    }

    @Override
    public CommandStatusResponse_ getResponse() {
        return response;
    }
}
