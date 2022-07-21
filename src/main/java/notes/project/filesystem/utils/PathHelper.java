package notes.project.filesystem.utils;

public interface PathHelper {
    String createPathToCluster(String clusterTile);
    String createPathToDirectory(String clusterTitle, String directoryTitle);
    String createPathToFile(String clusterId, String directoryId, String fileId);
}
