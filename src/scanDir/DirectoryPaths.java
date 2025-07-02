package scanDir;

import java.io.File;
import java.util.ArrayList;

public class DirectoryPaths {
    private File localDir;
    private File sortDir;

    public File getLocalDir() {
        return localDir;
    }

    public File getSortDir() {
        return sortDir;
    }

    public void setLocalDir(File localDir) {
        this.localDir = localDir;
    }

    public void setSortDir(File sortDir) {
        this.sortDir = sortDir;
    }
}
