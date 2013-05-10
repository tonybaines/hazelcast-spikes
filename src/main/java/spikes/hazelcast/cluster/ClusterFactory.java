package spikes.hazelcast.cluster;

public class ClusterFactory {
  private int instances;

  public static ClusterFactory aCluster() {
    return new ClusterFactory();
  }

  public ClusterFactory of(int numberOfInstances) {
    this.instances = numberOfInstances;
    return this;
  }

  public MyCluster start() {
    return new MyCluster(this.instances);
  }
}
