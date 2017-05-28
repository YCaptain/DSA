package graph.impl;

import graph.core.BFSLabel;
import graph.core.DFSLabel;
import graph.core.IEdge;
import graph.core.IGraph;
import graph.core.IIterator;
import graph.core.IList;
import graph.core.INode;
import graph.core.IVertex;
import graph.util.DLinkedList;

public class AdjacencyListGraph<V,E> implements IGraph<V,E> {
	/**
	 * Inner class to represent a vertex in an adjacency list graph implementation
	 */
	private class AdjacencyListVertex implements IVertex<V> {
		// label for depth first search
		DFSLabel dfsLabel;
		
		// label for breath first search
		BFSLabel bfsLabel;
		
		// reference to a node in the vertex list
		INode<IVertex<V>> node;
		
		// element stored in this vertex
		V element;
		
		// incident edges list
		IList<IEdge<E>> incidentEdges;
		
		public AdjacencyListVertex(V element) {
			this.element = element;
			// create new (empty) list of incident edges
			incidentEdges = new DLinkedList<IEdge<E>>();
		}

		@Override
		public V element() {
			return element;
		}
		
		// It's useful to have a toString() method that can
		// return details about this object, so you can
		// print the object later and get useful information.
		// This one prints the element
		public String toString() {
			return element.toString();
		}
	}
	
	/**
	 * Inner class to represent an edge in an adjacency list graph implementation.
	 *
	 */
	private class AdjacencyListEdge implements IEdge<E> {
		// label for depth first search
		DFSLabel dfsLabel;
		
		// label for breath first search
		BFSLabel bfsLabel;
		
		// reference to a node in the edge list and adjacencyList
		INode<IEdge<E>> node, startIE, endIE;

		// element stored in this edge
		E element;
		
		// the start and end vertices that this edge connects
		AdjacencyListVertex start, end;
		
		// constructor to set the three fields
		public AdjacencyListEdge(AdjacencyListVertex start, AdjacencyListVertex end, E element) {
			this.start = start;
			this.end = end;
			this.element = element;
		}

		@Override
		public E element() {
			return element;
		}
		
		public String toString() {
			return element.toString();
		}
	}
	
	// vertex list
	private IList<IVertex<V>> vertices;

	// edge list
	private IList<IEdge<E>> edges;
	
	/**
	 * Constructor
	 */
	public AdjacencyListGraph() {
		// create new (empty) lists of edges and vertices
		vertices = new DLinkedList<IVertex<V>>();
		edges = new DLinkedList<IEdge<E>>();
	}

	@Override
	public IVertex<V>[] endVertices(IEdge<E> e) {
		// need to cast Edge type to AdjacencyListEdge
		AdjacencyListEdge edge = (AdjacencyListEdge) e;

		// create new array of length 2 that will contain
		// the edge's end vertices
		@SuppressWarnings("unchecked")
		IVertex<V>[] endpoints = new IVertex[2];

		// fill array
		endpoints[0] = edge.start;
		endpoints[1] = edge.end;


		return endpoints;
	}

	@Override
	public IVertex<V> opposite(IVertex<V> v, IEdge<E> e) {
		// find end points of Edge e
		IVertex<V>[] endpoints = endVertices(e);

		// return the end point that is not v
		if (endpoints[0].equals(v)) {
			return endpoints[1];
		} else if (endpoints[1].equals(v)) {
			return endpoints[0];
		}

		// Problem! e is not connected to v.
		throw new RuntimeException("Error: cannot find opposite vertex.");
	}

	@Override
	public boolean areAdjacent(IVertex<V> v, IVertex<V> w) {
		// need to cast Vertex type to AdjacencyListVertex
		AdjacencyListVertex vertexV = (AdjacencyListVertex) v;
		AdjacencyListVertex vertexW = (AdjacencyListVertex) w;
		
		// choose smaller one and iterate through all the edges in the incident sequence
		IIterator<IEdge<E>> it;
		if(vertexV.incidentEdges.size() <= vertexW.incidentEdges.size()) {
			it = vertexV.incidentEdges.iterator();
		} else {
			it = vertexW.incidentEdges.iterator();
		}
		
		while (it.hasNext()) {
			// must cast Object type to EdgeListEdge type
			AdjacencyListEdge edge = (AdjacencyListEdge) it.next();

			// edge connects v -> w (so they are adjacent)
			if ((edge.start.equals(v)) && (edge.end.equals(w)))
				return true;

			// edge connects w -> v (so they are adjacent)
			if ((edge.end.equals(v)) && (edge.start.equals(w)))
				return true;
		}
		// no edge was found that connects v to w.
		return false;
	}

	@Override
	public V replace(IVertex<V> v, V o) {
		AdjacencyListVertex vertex = (AdjacencyListVertex) v;
		// store old element that we should return
		V temp = vertex.element;

		// do the replacement
		vertex.element = o;

		// return the old value
		return temp;
	}

	@Override
	public E replace(IEdge<E> e, E o) {
		AdjacencyListEdge edge = (AdjacencyListEdge) e;
		E temp = edge.element;
		edge.element = o;
		return temp;
	}

	@Override
	public IVertex<V> insertVertex(V o) {
		// create new vertex
		AdjacencyListVertex vertex = new AdjacencyListVertex(o);

		// insert the vertex into the vertex list
		// (returns a reference to the new Node that was created)
		INode<IVertex<V>> node = vertices.insertLast(vertex);

		// this reference must be stored in the vertex,
		// to make it easier to remove the vertex later.
		vertex.node = node;
		
		// reference of incident edges
		

		// return the new vertex that was created
		return vertex;
	}

	@Override
	public IEdge<E> insertEdge(IVertex<V> v, IVertex<V> w, E o) {
		// need to cast Vertex type to AdjacencyListVertex
		AdjacencyListVertex vertexV = (AdjacencyListVertex) v;
		AdjacencyListVertex vertexW = (AdjacencyListVertex) w;
		
		// create new edge object
		AdjacencyListEdge edge = new AdjacencyListEdge(vertexV, vertexW, o);

		// insert into the edge list and store the reference to the node
		// in the edge object
		INode<IEdge<E>> n = edges.insertLast(edge);
		edge.node = n;
		
		// insert into the incident edges list
		INode<IEdge<E>> startIE = vertexV.incidentEdges.insertLast(edge);
		INode<IEdge<E>> endIE = vertexW.incidentEdges.insertLast(edge);
		
		// reference to adjacency list in vertex
		edge.startIE = startIE;
		edge.endIE = endIE;
		
		// return the new edge that was created
		return edge;
	}

	@Override
	public V removeVertex(IVertex<V> v) {
		// get incident edges and remove those
		IList<IEdge<E>> incidentEdges = ((AdjacencyListVertex)v).incidentEdges;

		while(!incidentEdges.isEmpty()) {
			removeEdge(incidentEdges.remove(incidentEdges.first()));
		}
		
		// now we can remove the vertex from the vertex list
		AdjacencyListVertex vertex = (AdjacencyListVertex) v;
		vertices.remove(vertex.node);

		// return the element of the vertex that was removed
		return vertex.element;
	}

	@Override
	public E removeEdge(IEdge<E> e) {
		// remove edge from edge list and return its element
		AdjacencyListEdge edge = (AdjacencyListEdge) e;
		edges.remove(edge.node);
		return edge.element;
	}

	@Override
	public IIterator<IEdge<E>> incidentEdges(IVertex<V> v) {
		return ((AdjacencyListVertex)v).incidentEdges.iterator();
	}

	@Override
	public IIterator<IVertex<V>> vertices() {
		return vertices.iterator();
	}

	@Override
	public IIterator<IEdge<E>> edges() {
		return edges.iterator();
	}
	
	/**
	 * labeling of the edges of g as discovery edges and back edges
	 */
	public void DFS(IGraph<V, E> g) {
		for(IIterator<IVertex<V>> uIterator = g.vertices(); uIterator.hasNext();) {
			IVertex<V> u = uIterator.next();
			setLabel(u, DFSLabel.UNEXPLORED);
		}
		for(IIterator<IEdge<E>> eIterator = g.edges(); eIterator.hasNext();) {
			IEdge<E> e = eIterator.next();
			setLabel(e, DFSLabel.UNEXPLORED);
		}
		for(IIterator<IVertex<V>> vIterator = g.vertices(); vIterator.hasNext();) {
			IVertex<V> v = vIterator.next();
			if(getLabel(v) == DFSLabel.UNEXPLORED)
				DFS(g, v);
		}
	}
	
	/**
	 * labeling of the edges of g in the connected component of v as
	 * discovery edges and back edges
	 */
	public void DFS(IGraph<V, E> g, IVertex<V> v) {
		setLabel(v, DFSLabel.VISITED);
		for(IIterator<IEdge<E>> eIterator = g.incidentEdges(v); eIterator.hasNext();) {
			IEdge<E> e = eIterator.next();
			if(getLabel(e) == DFSLabel.UNEXPLORED) {
				IVertex<V> w = g.opposite(v, e);
				if(getLabel(w) == DFSLabel.UNEXPLORED) {
					setLabel(e, DFSLabel.DISCOVERY);
					DFS(g, w);
				} else {
					setLabel(e, DFSLabel.BACK);
				}
			}
		}
		
	}
		
	/**
	 * labeling vertex
	 */
	@SuppressWarnings("unchecked")
	public void setLabel(IVertex<V> u, DFSLabel l) {
		// must cast to EdgeList vertex
		AdjacencyListVertex v = (AdjacencyListVertex) u;
		v.dfsLabel = l;
	}
		
	/**
	 * labeling edge
	 */
	public void setLabel(IEdge<E> e, DFSLabel l) {
		// must cast to EdgeList edge
		AdjacencyListEdge ed = (AdjacencyListEdge) e;
		ed.dfsLabel = l;	
	}
		
	/**
	 * get label of vertex
	 */
	public DFSLabel getLabel(IVertex<V> v) {
		// must cast to EdgeList vertex
		AdjacencyListVertex ve = (AdjacencyListVertex) v;
		return ve.dfsLabel;
	}

	/**
	 * get label of edge
	 */
	public DFSLabel getLabel(IEdge<E> e) {
		// must cast to EdgeList edge
		AdjacencyListEdge ed = (AdjacencyListEdge) e;
		return ed.dfsLabel;
	}
	
	/**
	 * labeling of the edges of g as discovery edges and back edges
	 */
	public void BFS(IGraph<V, E> g) {
		for(IIterator<IVertex<V>> uIterator = g.vertices(); uIterator.hasNext();) {
			IVertex<V> u = uIterator.next();
			setBLabel(u, BFSLabel.UNEXPLORED);
		}
		for(IIterator<IEdge<E>> eIterator = g.edges(); eIterator.hasNext();) {
			IEdge<E> e = eIterator.next();
			setBLabel(e, BFSLabel.UNEXPLORED);
		}
		for(IIterator<IVertex<V>> vIterator = g.vertices(); vIterator.hasNext();) {
			IVertex<V> v = vIterator.next();
			if(getBLabel(v) == BFSLabel.UNEXPLORED)
				DFS(g, v);
		}
	}
	
	/**
	 * labeling of the edges of g in the connected component of v as
	 * discovery edges and back edges
	 */
	public void BFS(IGraph<V, E> g, IVertex<V> s) {
		IList<IVertex<V>>[] l = new DLinkedList[1];
		l[0].insertLast(s);
		setBLabel(s, BFSLabel.VISITED);
		int i = 0;
		while(!l[i].isEmpty()) {
			IList<IVertex<V>>[] newL = new DLinkedList[i+2];
			for(int j = 0; j <= i; j++) {
				newL[j] = l[j];
			}
			l = newL;
			
			for(IIterator<IVertex<V>> vIterator = l[i].iterator(); vIterator.hasNext();) {
				IVertex<V> v = vIterator.next();
				for(IIterator<IEdge<E>> eIterator = g.incidentEdges(v); eIterator.hasNext();) {
					IEdge<E> e = eIterator.next();
					if(getBLabel(e) == BFSLabel.UNEXPLORED) {
						IVertex<V> w = opposite(v, e);
						if(getBLabel(w) == BFSLabel.UNEXPLORED) {
							setBLabel(e, BFSLabel.DISCOVERY);
							setBLabel(w, BFSLabel.VISITED);
							l[i+1].insertLast(w);
						} else {
							setBLabel(e, BFSLabel.CROSS);
						}
					}
				}
			}
			i++;
		}
		
		
	}
	
	/**
	 * labeling vertex
	 */
	@SuppressWarnings("unchecked")
	public void setBLabel(IVertex<V> u, BFSLabel l) {
		// must cast to EdgeList vertex
		AdjacencyListVertex v = (AdjacencyListVertex) u;
		v.bfsLabel = l;
	}
		
	/**
	 * labeling edge
	 */
	public void setBLabel(IEdge<E> e, BFSLabel l) {
		// must cast to EdgeList edge
		AdjacencyListEdge ed = (AdjacencyListEdge) e;
		ed.bfsLabel = l;	
	}
	
	/**
	 * get label of vertex
	 */
	public BFSLabel getBLabel(IVertex<V> v) {
		// must cast to EdgeList vertex
		AdjacencyListVertex ve = (AdjacencyListVertex) v;
		return ve.bfsLabel;
	}

	/**
	 * get label of edge
	 */
	public BFSLabel getBLabel(IEdge<E> e) {
		// must cast to EdgeList edge
		AdjacencyListEdge ed = (AdjacencyListEdge) e;
		return ed.bfsLabel;
	}

}
