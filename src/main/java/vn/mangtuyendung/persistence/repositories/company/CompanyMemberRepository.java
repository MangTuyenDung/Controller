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
package vn.mangtuyendung.persistence.repositories.company;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import vn.mangtuyendung.persistence.domain.company.CompanyMemberDomain;

/*
 * CompanyMemberRepository.java
 * 
 * Version 1.0
 *
 * Date 01/01/2013
 * 
 * Company member repository
 * 
 * Modification Logs:
 *  DATE            AUTHOR      DESCRIPTION
 *  --------------------------------------------------------
 *  01-Jan-2013     tuanta      Create first time
 */
public interface CompanyMemberRepository {

    public List<CompanyMemberDomain> findAll();

    public CompanyMemberDomain findById(@Param("id") long id);

    public List<CompanyMemberDomain> findByMemberId(@Param("memberId") long memberId);

    public void save(CompanyMemberDomain domain);

    public void update(CompanyMemberDomain domain);

    public void delete(@Param("id") long id, @Param("memberId") long memberId);
}
