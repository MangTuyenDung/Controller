/*
 * Copyright 2012 Hadoop Vietnam <admin@hadoopvietnam.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package vn.mangtuyendung.persistence.repositories;

import java.io.IOException;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.SolrInputDocument;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vn.mangtuyendung.solr.SolrService;

/**
 *
 * @author Hadoop Vietnam <admin@hadoopvietnam.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-mybatis.xml", "classpath:META-INF/spring/applicationContext-service.xml"})
public class SolrServiceTest {

    @Autowired
    CategoryMapRepository repository;
    @Autowired
    private SolrService service;

//    @Test
//    public void testFind() throws SolrServerException {
//        int size = 100;
//        String query = "-jobOverview:*";
//        QueryResponse reponse = service.search(query, 0, size);
//        int total = (int) reponse.getResults().getNumFound();
//        int page = total / size;
//        for (int i = 0; i <= page; i++) {
//            reponse = service.search(query, i * size, (i + 1) * size);
//            SolrDocumentList list = reponse.getResults();
//            for (SolrDocument solrDocument : list) {
//                String tmp = solrDocument.getFieldValue("sourceCategory").toString();
//                String[] cat = tmp.split(",");
//                String domain = solrDocument.getFieldValue("domain").toString();
//                for (String c : cat) {
//                    CategoryMapDomain find = repository.findByDomainAndCategory(domain, c);
//                    if (find == null) {
//                        CategoryMapDomain _domain = new CategoryMapDomain();
//                        _domain.setCat(c);
//                        _domain.setDomain(domain);
//                        _domain.setMap("");
//                        System.out.println("Save to map db - Domain:" + domain + "-Category:" + c);
//                        repository.save(_domain);
//                    }
//                }
//            }
//        }
//    }
    
//    @Test
//    public void testUpdateMap() throws SolrServerException {
//        int size = 100;
//        String query = "-jobCategory:*";
//        QueryResponse reponse = service.search(query, 0, size);
//        int total = (int) reponse.getResults().getNumFound();
//        int page = total / size;
//        for (int i = 0; i <= page; i++) {
//            reponse = service.search(query, i * size, (i + 1) * size);
//            SolrDocumentList list = reponse.getResults();
//            for (SolrDocument solrDocument : list) {
//                String tmp = solrDocument.getFieldValue("sourceCategory").toString();
//                String[] cat = tmp.split(",");
//                String domain = solrDocument.getFieldValue("domain").toString();
//                for (String c : cat) {
//                    CategoryMapDomain find = repository.findByDomainAndCategory(domain, c);
//                    if (find == null) {
//                        CategoryMapDomain _domain = new CategoryMapDomain();
//                        _domain.setCat(c);
//                        _domain.setDomain(domain);
//                        _domain.setMap("");
//                        System.out.println("Save to map db - Domain:" + domain + "-Category:" + c);
//                        repository.save(_domain);
//                    }
//                }
//            }
//        }
//    }
    
//    @Test
//    public void testUpdateLocation() throws SolrServerException, IOException {
//        SolrServer source = new HttpSolrServer("http://localhost:8983/solr");
//        int size = 100;
//        String query = "jobLocation:\"Huế\"";
//        QueryResponse reponse = service.search(query, 0, size);
//        int total = (int) reponse.getResults().getNumFound();
//        int page = total / size;
//        for (int i = 0; i <= page; i++) {
//            reponse = service.search(query, i * size, (i + 1) * size);
//            SolrDocumentList list = reponse.getResults();
//            for (SolrDocument solrDocument : list) {
//                SolrInputDocument doc = ClientUtils.toSolrInputDocument(solrDocument);
//                doc.removeField("signature");
//                doc.removeField("_version_");
//                String tmp = doc.getFieldValue("jobLocation").toString().trim();
//                doc.setField("jobLocation", "Thừa Thiên Huế");
//                postDocument(source, doc);
//            }
//        }
//        source.commit();
//    }

//    @Test
//    public void testUpdateCategory() throws SolrServerException, IOException {
//        SolrServer source = new HttpSolrServer("http://localhost:8983/solr");
//        int size = 100;
//        String query = "jobCategory:\"\"";
//        QueryResponse reponse = service.search(query, 0, size);
//        int total = (int) reponse.getResults().getNumFound();
//        int page = total / size;
//        for (int i = 0; i <= page; i++) {
//            reponse = service.search(query, i * size, (i + 1) * size);
//            SolrDocumentList list = reponse.getResults();
//            for (SolrDocument solrDocument : list) {
//                SolrInputDocument doc = ClientUtils.toSolrInputDocument(solrDocument);
//                doc.removeField("signature");
//                doc.removeField("_version_");
//                String tmp = doc.getFieldValue("sourceCategory").toString().trim();
//                String[] cats = tmp.split(",");
//                String domain = solrDocument.getFieldValue("domain").toString();
//                List<String> _list = new ArrayList<String>();
//                for (String cat : cats) {
//                    CategoryMapDomain find = repository.findByDomainAndCategory(domain, cat);
//                    if (find != null) {
//                        tmp = find.getMap();
//                        String[] maps = tmp.split("/");
//                        for (String m : maps) {
//                            if (!_list.contains(m)) {
//                                _list.add(m);
//                            }
//                        }
//                    }
//                }
//                doc.setField("jobCategory", _list);
//                postDocument(source, doc);
//            }
//        }
//        source.commit();
//    }
//    @Test
//    public void testUpdateEducationLevel() throws SolrServerException, IOException {
//        SolrServer source = new HttpSolrServer("http://localhost:8983/solr");
//        int size = 100;
//        String query = "jobEducationLevel:\"Không khai báo\"";
//        QueryResponse reponse = service.search(query, 0, size);
//        int total = (int) reponse.getResults().getNumFound();
//        int page = total / size;
//        for (int i = 0; i <= page; i++) {
//            reponse = service.search(query, i * size, (i + 1) * size);
//            SolrDocumentList list = reponse.getResults();
//            for (SolrDocument solrDocument : list) {
//                SolrInputDocument doc = ClientUtils.toSolrInputDocument(solrDocument);
//                doc.removeField("signature");
//                doc.removeField("_version_");
//                String tmp = doc.getFieldValue("jobEducationLevel").toString();
//                tmp = tmp.replaceAll("Không khai báo", "Không yêu cầu");
//                tmp = tmp.replaceAll("Khác", "Không yêu cầu");
//                tmp = tmp.replaceAll("PTCS", "Trung học");
//                tmp = tmp.replaceAll("Kỹ sư", "Đại học");
//                tmp = tmp.replaceAll("Cử nhân", "Đại học");
//                tmp = tmp.replaceAll("Thạc sĩ QTKD", "Trên đại học");
//                tmp = tmp.replaceAll("Thạc sĩ", "Trên đại học");
//                tmp = tmp.replaceAll("Cao học", "Trên đại học");
//                doc.setField("jobEducationLevel", tmp);
//                postDocument(source, doc);
//            }
//        }
//        source.commit();
//    }
    private void postDocument(SolrServer source, SolrInputDocument doc) throws SolrServerException, IOException {
        try {
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.add(doc);
            updateRequest.process(source);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
