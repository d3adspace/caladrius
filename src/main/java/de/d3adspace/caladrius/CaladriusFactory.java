package de.d3adspace.caladrius;

import com.google.common.collect.Maps;

public final class CaladriusFactory {

  public static Caladrius createCaladrius() {

    return new DefaultCaladrius(Maps.newConcurrentMap());
  }
}
