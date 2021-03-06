package org.linkeddatafragments.model;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.util.iterator.Filter;
import com.hp.hpl.jena.util.iterator.Map1;
import com.hp.hpl.jena.util.iterator.WrappedIterator;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.linkeddatafragments.client.LinkedDataFragmentsClient;

/**
 * Created by ldevocht on 4/29/14.
 */
public class ExtendedTripleIteratorLDF implements ExtendedIterator<Triple> {
    protected ExtendedIterator<Triple> triples;
    protected Iterator<LinkedDataFragment> ldfIterator;

    public ExtendedTripleIteratorLDF(LinkedDataFragmentsClient ldfClient, LinkedDataFragment ldf) {
        triples = ldf.getTriples();
        ldfIterator = LinkedDataFragmentIterator.create(ldf, ldfClient);
    }

    public static ExtendedIterator<Triple> create(LinkedDataFragmentsClient ldfClient, LinkedDataFragment ldf) {
        return new ExtendedTripleIteratorLDF(ldfClient, ldf);
    }

    
    public Triple removeNext() {
        return triples.removeNext();
    }

    
    public <X extends Triple> ExtendedIterator<Triple> andThen(Iterator<X> other) {
        throw new UnsupportedOperationException();
    }

    
    public ExtendedIterator<Triple> filterKeep(Filter<Triple> f) {
        return triples.filterKeep(f);
    }

    
    public ExtendedIterator<Triple> filterDrop(Filter<Triple> f) {
        return triples.filterDrop(f);
    }

    
    public <U> ExtendedIterator<U> mapWith(Map1<Triple, U> map1) {
        return triples.mapWith(map1);
    }

    
    public List<Triple> toList() {
        return triples.toList();
    }

    
    public Set<Triple> toSet() {
        return triples.toSet();
    }

    
    public void close() {
        triples.close();
    }

    
    public boolean hasNext() {
        Boolean hasNext = triples.hasNext();
        if(!hasNext) {
            if(ldfIterator.hasNext()) {
                triples = ldfIterator.next().getTriples();
                return true;
            } else {
                return false;
            }
        }

        return hasNext;
    }

    
    public Triple next() {
        Boolean hasNext = triples.hasNext();
        if(!hasNext) {
            if(ldfIterator.hasNext()) {
                triples = ldfIterator.next().getTriples();
                return triples.next();
            } else {
                return null;
            }
        }
        return triples.next();
    }

    
    public void remove() {

    }
}
