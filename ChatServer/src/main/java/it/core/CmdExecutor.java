package it.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface CmdExecutor {
    void execute(String[] arguments) throws IOException;
}
