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
package org.camunda.bpm.examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.benchmark.plugin.MetricsPlugin;
import org.camunda.bpm.engine.impl.ProcessEngineImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;

/**
 * @author Daniel Meyer
 *
 */
@WebServlet(urlPatterns = {"/metrics/*"})
public class MetricsServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public void init() throws ServletException {
    super.init();
    if(getMetricsPlugin() == null) {
      throw new RuntimeException("Could not detect MetricsPlugin.");
    }
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if(req.getRequestURI().endsWith("stop")) {
      writeAggregationsToResponse(resp);
    }
    getMetricsPlugin().getCommandCounter().clear();
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    writeAggregationsToResponse(resp);
  }

  protected void writeAggregationsToResponse(HttpServletResponse resp) throws IOException {
    List<Long> commandAggregations = getMetricsPlugin().getCommandCounter().getAggregations();
    resp.setContentType("application/json");
    PrintWriter respWriter = resp.getWriter();
    respWriter.write("[");
    for (int i = 0; i < commandAggregations.size(); i++) {
      if(i != 0) {
        respWriter.write(", ");
      }
      respWriter.write(String.valueOf(commandAggregations.get(i)));
    }
    respWriter.write("]\n");
  }

  protected MetricsPlugin getMetricsPlugin() {
    ProcessEngineImpl processEngine = (ProcessEngineImpl) BpmPlatform.getDefaultProcessEngine();
    List<ProcessEnginePlugin> processEnginePlugins = processEngine.getProcessEngineConfiguration().getProcessEnginePlugins();
    for (ProcessEnginePlugin processEnginePlugin : processEnginePlugins) {
      if(processEnginePlugin instanceof MetricsPlugin) {
        return (MetricsPlugin) processEnginePlugin;
      }
    }
    return null;
  }

}
