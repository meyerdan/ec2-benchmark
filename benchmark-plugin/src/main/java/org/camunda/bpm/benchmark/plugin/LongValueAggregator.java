/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.benchmark.plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Daniel Meyer
 *
 */
public class LongValueAggregator {

  protected final AtomicLong counter = new AtomicLong();

  protected final long aggregationInterval;

  protected final List<Long> aggregations = Collections.synchronizedList(new LinkedList<Long>());

  public LongValueAggregator(long aggregationInterval) {
    this.aggregationInterval = aggregationInterval;
  }

  public void increment() {

    // lock-free implementation of either incrementing the counter
    // or resetting the counter to 0 if aggregation interval is reached
    long prev, next, inc;
    do {
        prev = counter.get();
        inc = prev + 1;
        if(inc == aggregationInterval) {
          next = 0;
        }
        else {
          next = inc;
        }
    } while (!counter.compareAndSet(prev, next));

    // logging the timestamp if aggregation interval is reached.
    // (this uses synchronization, see synchronizedList() above)
    if(inc == aggregationInterval) {
      aggregations.add(System.currentTimeMillis());
    }

  }

  public List<Long> getAggregations() {
    // aggregations may be logged out of order
    List<Long> copy = new ArrayList<Long>(aggregations);
    Collections.sort(copy, new Comparator<Long>() {
      public int compare(Long o1, Long o2) {
        return o1.compareTo(o2);
      }
    });
    return copy;
  }

  public void clear() {
    aggregations.clear();
  }

}
