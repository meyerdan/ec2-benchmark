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

import org.camunda.bpm.engine.impl.cmd.AcquireJobsCmd;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandInterceptor;

/**
 *
 * @author Daniel Meyer
 *
 */
public class CommandCounterInterceptor extends CommandInterceptor {

  protected LongValueAggregator aggregator;

  public CommandCounterInterceptor(LongValueAggregator aggregator) {
    this.aggregator = aggregator;
  }

  public <T> T execute(Command<T> command) {

    if(!(command instanceof AcquireJobsCmd)) {
      aggregator.increment();
    }

    return next.execute(command);
  }

}
