package vn.mangtuyendung.controller.admin;

import com.hadoopvietnam.persistence.domain.AccountDomain;
import com.hadoopvietnam.service.AccountService;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.mangtuyendung.controller.SiteAbstractController;

@RequestMapping({"/admin/account"})
@Controller
public class AdminAccountController extends SiteAbstractController {

    private Logger logger = LoggerFactory.getLogger(AdminApplyController.class);
    @Autowired
    private AccountService accountService;

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String index(Model model) {
        try {
            int sizeNo = 10;
            Collection<AccountDomain> accounts = this.accountService.findByLimit("CREATED DESC", 0, sizeNo);
            model.addAttribute("topaccounts", accounts);
            long total = this.accountService.count();
            float nrOfPages = (float) total / sizeNo;
            model.addAttribute("totalaccount", Long.valueOf(total));
            model.addAttribute("maxPages", Integer.valueOf((int) ((nrOfPages > (int) nrOfPages) || (nrOfPages == 0.0D) ? nrOfPages + 1.0F : nrOfPages)));

            return "admin/members";
        } catch (Exception ex) {
            this.logger.error("Cannot get data." + ex.toString());
        }
        return "home/maintenance";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @RequestMapping(value = {"/p{page}-{size}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String indexPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size, Model model) {
        try {
            Collection<AccountDomain> accounts;
            int sizeNo;
            sizeNo = size == null ? 10 : size.intValue();
            page = Integer.valueOf(page == null ? 0 : page.intValue() - 1);
            accounts = this.accountService.findByLimit("CREATED DESC", page.intValue() * sizeNo, sizeNo);
            long total = this.accountService.count();
            float nrOfPages = (float) total / sizeNo;
            model.addAttribute("maxPages", Integer.valueOf((int) ((nrOfPages > (int) nrOfPages) || (nrOfPages == 0.0D) ? nrOfPages + 1.0F : nrOfPages)));

            model.addAttribute("totalaccount", Long.valueOf(total));
            model.addAttribute("topaccounts", accounts);
            model.addAttribute("pageNo", Integer.valueOf(page.intValue() + 1));
            model.addAttribute("sizeNo", size);
            return "admin/members";
        } catch (Exception ex) {
            this.logger.error("Cannot get data." + ex.toString());
        }
        return "home/maintenance";
    }
}