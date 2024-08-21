package com.chrzanowy.cron.expressions;

import com.chrzanowy.cron.elements.Element;
import java.util.Set;

public interface Expression {

    Set<Integer> evaluate();

    Element getElement();

}
