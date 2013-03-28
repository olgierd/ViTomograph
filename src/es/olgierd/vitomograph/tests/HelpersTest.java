package es.olgierd.vitomograph.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import es.olgierd.vitomograph.utils.Helpers;

public class HelpersTest {

    @Test
    public void test() {
	
	
	double[] a = { 1, 2, 3, 4 };
	double[] b = { -1, 5, 3 };
	
	double res[] = Helpers.convoluteSignals(a, b);
	
	for(double d : res) {
	    System.out.println(d);
	}
	
    }

}
