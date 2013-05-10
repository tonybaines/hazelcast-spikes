package spikes.hazelcast.cluster;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyCluster {
  private final AtomicInteger instanceSequence = new AtomicInteger(1);
  private final Map<String, HazelcastInstance> instances = new HashMap<String, HazelcastInstance>();

  public MyCluster(int instances) {
    for (int i = 0; i < instances; i++) {
      addNode();
    }
  }

  public void shutdownNode(String nodeId) {
    this.instances.get(nodeId).getLifecycleService().shutdown();
  }

  public void shutdownAllNodes() {
    for (String nodeId : instances.keySet()) {
      shutdownNode(nodeId);
    }
  }

  public boolean nodeIsActive(String nodeId) {
    HazelcastInstance instance = this.instances.get(nodeId);
    return instance != null && instance.getLifecycleService().isRunning();
  }

  public boolean allNodesActive() {
    for (String nodeId : instances.keySet()) {
      if (!nodeIsActive(nodeId)) {
        System.out.println(String.format("The node %s is *not* running.", nodeId));
        return false;
      }
    }
    return true;
  }

  /**
   * The Cluster is active if any of it's node are active
   *
   * @return
   */
  public boolean isActive() {
    boolean active = true;
    for (String nodeId : instances.keySet()) {
      if (nodeIsActive(nodeId)) {
        System.out.println(String.format("Node %s is active", nodeId));
        active = active && true;
      }
    }
    return active;
  }

  public void addNode() {
    this.instances.put(Integer.toString(instanceSequence.getAndIncrement()), Hazelcast.newHazelcastInstance());
  }

  public int activeNodesCount() {
    int activeNodeCount = 0;
    for (String nodeId : instances.keySet()) {
      if (nodeIsActive(nodeId)) {
        activeNodeCount++;
      }
    }
    return activeNodeCount;
  }
}
