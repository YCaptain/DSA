import graph.core.IEdge;
import graph.core.IGraph;
import graph.core.IIterator;
import graph.core.IVertex;
import graph.impl.AdjacencyListGraph;
import graph.impl.AdjacencyMatrixGraph;

public class AdjacencyMatrixTest {
	  public static void main( String[] args ) throws Exception {
	      IGraph<String,Integer> g = new AdjacencyMatrixGraph<String,Integer>();
	      // create some vertices
	      IVertex<String> hnl = g.insertVertex( "HNL" );
	      IVertex<String> lax = g.insertVertex( "LAX" );
	      IVertex<String> sfo = g.insertVertex( "SFO" );
	      IVertex<String> ord = g.insertVertex( "ORD" );
	      IVertex<String> dfw = g.insertVertex( "DFW" );
	      IVertex<String> lga = g.insertVertex( "LGA" );
	      IVertex<String> pvd = g.insertVertex( "PVD" );
	      IVertex<String> mia = g.insertVertex( "MIA" );

	      // create some edges
	      IEdge<Integer> hnllax = g.insertEdge( hnl, lax, 2555 );
	      IEdge<Integer> laxsfo = g.insertEdge( lax, sfo, 337 );
	      IEdge<Integer> ordsfo = g.insertEdge( ord, sfo, 1843 );
	      IEdge<Integer> laxord = g.insertEdge( lax, ord, 1743 );
	      IEdge<Integer> dfwlax = g.insertEdge( dfw, lax, 1233 );
	      IEdge<Integer> ordpvd = g.insertEdge( ord, pvd, 849 );
	      IEdge<Integer> dfwlga = g.insertEdge( dfw, lga, 1387 );
	      IEdge<Integer> dfwmia = g.insertEdge( dfw, mia, 1120 );
	      IEdge<Integer> lgamia = g.insertEdge( lga, mia, 1099 );
	      IEdge<Integer> lgapvd = g.insertEdge( lga, pvd, 142 );
	      
	      // sample test for areAdjacent
	      if ( g.areAdjacent( sfo,  ord ) )
	         System.out.println( "SFO and ORD adjacent: correct" );
	      else
	         System.out.println( "SFO and ORD adjacent: incorrect" );
	      
	      // sample test for endVertices
	      IVertex<String>[] ends = g.endVertices( laxord );
	      if ( ( ends[0] == lax && ends[1] == ord ) ||
	           ( ends[1] == lax && ends[0] == ord ) )
	         System.out.println( "End vertices of LAX<->ORD: correct" );
	      else
	         System.out.println( "End vertices of LAX<->ORD: incorrect" );
	      
	      // sample test for opposite
	      if ( g.opposite( pvd, lgapvd ) == lga )
	         System.out.println( "Opposite of PVD along LGA<->PVD: correct" );
	      else
	         System.out.println( "Opposite of PVD along LGA<->PVD: incorrect" );
	      
	      // example of getting an object from the graph 
	      String miaElement = mia.element();
	      System.out.println( "Element of MIA is: " + miaElement );
	      
	      // the edge labels were set as type int.
	      int dfwlaxElement = dfwlax.element();
	      System.out.println( "Distance from DFW to LAX is: " + dfwlaxElement );
	      
	      // print names of all vertices
	      IIterator<IVertex<String>> it = g.vertices();
	      while( it.hasNext() ) {
	         // here I must cast also, since it.next() returns an Object
	         IVertex<String> v = it.next();
	         System.out.println( v.element() );
	      }
	      
	      // print distance and its vertices of all edges 
	      IIterator<IEdge<Integer>> ie = g.edges();
	      while(ie.hasNext()) {
	    	  IEdge<Integer> w = ie.next();
	    	  IVertex<String>[] ver = g.endVertices(w);
	    	  System.out.println(ver[0].element() + "--" + w.element() + "--" + ver[1].element());
	      }
	      
	      // test for remove vertex
	      it = g.vertices();
	      int count = 0;
	      while( it.hasNext() ) {
	          // here I must cast also, since it.next() returns an Object
	          IVertex<String> v = it.next();
	          count++;
	      }
	      System.out.println("count: " + count);
	      g.removeVertex(hnl);
	      it = g.vertices();
	      count = 0;
	      while( it.hasNext() ) {
	          // here I must cast also, since it.next() returns an Object
	          IVertex<String> v = it.next();
	          count++;
	       }
	      System.out.println("remove hnl vertex, count: " + count);
	      
	      // test for remove edge
	      ie = g.edges();
	      count = 0;
	      while(ie.hasNext()) {
	    	  ie.next();
	    	  count++;
	      }
	      System.out.println("edges count: " + count);
	      g.removeEdge(lgamia);
	      ie = g.edges();
	      count = 0;
	      while(ie.hasNext()) {
	    	  ie.next();
	    	  count++;
	      }
	      System.out.println("remove lgamia edge, count: " + count);
	 
	      // test for depth first search
	      AdjacencyMatrixGraph<String,Integer> amg = (AdjacencyMatrixGraph<String,Integer>) g;
	      amg.DFS(amg);
	      
	      // test for depth first search
		  AdjacencyMatrixGraph<String,Integer> elg = (AdjacencyMatrixGraph<String,Integer>) g;
	      try{
	    	  elg.DFS(elg);
	    	  System.out.println("DFS success!!");
	      } catch(Exception e) {
	    	  e.printStackTrace();
	      }
	 
	      
	      // test for breath first search
	      try{
	    	  elg.BFS(elg);
	    	  System.out.println("BFS success!!");
	      } catch(Exception e) {
	    	  e.printStackTrace();
	      }
	      
	  }
}
