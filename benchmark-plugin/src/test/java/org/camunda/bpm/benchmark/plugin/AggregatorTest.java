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

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author Daniel Meyer
 *
 */
public class AggregatorTest {

  @Test
  public void testIncrement() {
    LongValueAggregator longValueAggregator = new LongValueAggregator(3);
    longValueAggregator.increment();
    Assert.assertEquals(0, longValueAggregator.getAggregations().size());
    longValueAggregator.increment();
    Assert.assertEquals(0, longValueAggregator.getAggregations().size());
    longValueAggregator.increment();
    Assert.assertEquals(1, longValueAggregator.getAggregations().size());
    longValueAggregator.increment();
    Assert.assertEquals(1, longValueAggregator.getAggregations().size());
    longValueAggregator.increment();
    Assert.assertEquals(1, longValueAggregator.getAggregations().size());
    longValueAggregator.increment();
    Assert.assertEquals(2, longValueAggregator.getAggregations().size());
  }

}
