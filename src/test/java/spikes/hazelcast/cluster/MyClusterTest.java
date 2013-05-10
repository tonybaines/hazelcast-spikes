package spikes.hazelcast.cluster;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MyClusterTest {
  private MyCluster cluster;

  @Before
  public void startAllNodes() {
    ClusterFactory clusterFactory = ClusterFactory.aCluster().of(2);
    cluster = clusterFactory.start();
  }




  @Test
  public void allNodesShouldBeActive() {
    assertThat(cluster.allNodesActive(), is(true));
  }

  @Test
  public void stoppingOneNodeDoesNotStopTheCluster() {
    cluster.shutdownNode("1");

    assertThat(cluster.nodeIsActive("1"), is(false));
    assertThat(cluster.nodeIsActive("2"), is(true));
    assertThat(cluster.activeNodesCount(), is(1));

    assertThat(cluster.isActive(), is(true));
  }

  @Test
  public void aNewNodeCanBeAddedToTheCluster() {
    cluster.addNode();
    assertThat(cluster.activeNodesCount(), is(3));
    assertThat(cluster.isActive(), is(true));
  }





  @After
  public void stopAllNodes() {
    cluster.shutdownAllNodes();
  }

}
