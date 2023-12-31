package requestLogic.requestSenders;

import exceptions.NotAvailableServerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import requests.BaseRequest_;
import responseLogic.ResponseReader;
import responses.BaseResponse_;
import serverLogic.ServerConnection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;

public class RequestSender {
    private static final Logger logger = LogManager.getLogger("io.github.Mekek.lab6");

    public BaseResponse_ sendRequest(BaseRequest_ request, ServerConnection connection) throws IOException, NotAvailableServerException {
        BaseResponse_ response = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(request);
            logger.info("Request sent");
            InputStream responseStream = connection.sendData(bos.toByteArray());
            if (responseStream != null) {
                ResponseReader reader = new ResponseReader(responseStream);
                response = reader.readObject();
                logger.info("Received response");
            } else logger.info("Received null");
        } catch (ClassNotFoundException e) {
            logger.error("Response class not found.");
        }
        return response;
    }
}
