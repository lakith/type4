package com.spring.starter.configuration;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.starter.jwt.JwtTokenAuthenticationFilter;

@EnableWebSecurity
@Configuration
@ComponentScan("com.spring.starter")
@Order(3)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationConfig config;

    private String prfix = "api/ndb";

    @Bean
    public JwtAuthenticationConfig jwtConfig() {
        return new JwtAuthenticationConfig();
    }

    @Autowired
    DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Enable jdbc authentication
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder());
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() throws Exception {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);
        return jdbcUserDetailsManager;
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().disable()
                .csrf().disable()
                .logout().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous()
                .and()
                .exceptionHandling().authenticationEntryPoint(
                (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(config),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(config.getUrl()).permitAll()
                .antMatchers("/staffUsers/addNewUser").hasRole("ADMIN")
                .antMatchers("/staffUsers/addStaffUserFirstTime").permitAll()
                .antMatchers("/staffUsers/login/**").permitAll()
                .antMatchers("/staffRoles").permitAll()
                .antMatchers("/CustomerServiceRequest/**").permitAll()
                .antMatchers("/serviceRequest/atmOrDebit/request").permitAll()
                .antMatchers("/serviceRequest/atmOrDebit/reIssuePin").permitAll()
                .antMatchers("/serviceRequest/atmOrDebit/smsSubscription").permitAll()
                .antMatchers("/serviceRequest/atmOrDebit/posLimit").permitAll()
                .antMatchers("/serviceRequest/atmOrDebit/LinkedAccount").permitAll()
                .antMatchers("/serviceRequest/atmOrDebit/changePrimaryAccount").permitAll()
                .antMatchers("/bankStatementPassBook/duplicatePassbook").permitAll()
                .antMatchers("/bankStatementPassBook/AccountStatement").permitAll()
                .antMatchers("/bankStatementPassBook/e-statement").permitAll()
                .antMatchers("/bankStatementPassBook/statementFrequency").permitAll()
                .antMatchers("/serviceRequest/address/change-mailing").permitAll()
                .antMatchers("/serviceRequest/changePermanentAddress").permitAll()
                .antMatchers("/serviceRequest/CustomerServiceRequest").permitAll()
                .antMatchers("/serviceRequest/addRelatedRequest").permitAll()
                .antMatchers("/serviceRequest/addDuplicateFdCdCert").permitAll()
                .antMatchers("/serviceRequest/internet-banking/reissue-password").permitAll()
                .antMatchers("/serviceRequest/link-JointAccounts").permitAll()
                .antMatchers("/serviceRequest/exclude-accounts").permitAll()
                .antMatchers("/serviceRequest/other-service").permitAll()
                .antMatchers("/serviceRequest/address/change-permenenet/").permitAll()
                .antMatchers("/serviceRequest/otherRequest").permitAll()
                .antMatchers("/serviceRequest/addNewBankService").permitAll()
                .antMatchers("/serviceRequest/getBankServices").permitAll()
                .antMatchers("/serviceRequest/addNewCustomer").permitAll()
                .antMatchers("/serviceRequest/addNewServiceToACustomer").permitAll()
                .antMatchers("/serviceRequest/getcustomer").permitAll()
                .antMatchers("/serviceRequest/getServicesOfACustomerByDate").permitAll()
                .antMatchers("/serviceRequest/getAllCustomerRequests").permitAll()
                .antMatchers("/serviceRequest/getAllCustomerDataWithRequests").permitAll()
                .antMatchers("/serviceRequest/completeACustomerRequest").permitAll()
                .antMatchers("/serviceRequest/addAStaffHandled").permitAll()
                .antMatchers("/serviceRequest/getServiceRequestForm").permitAll()
                .antMatchers("/serviceRequest/addSignature").permitAll()
                .antMatchers("/serviceRequest/SMSAlertsforCreditCard").permitAll()
                .antMatchers("/serviceRequest/get-all-customer-requests-filter-by-reject/**").permitAll()
                .antMatchers("/Transaction-Request/add-service").permitAll()
                .antMatchers("/Transaction-Request/view-transaction-request/**").permitAll()
                .antMatchers("/Transaction-Request/getServiceRequestForm").permitAll()
                .antMatchers("/Transaction-Request/ger-all-service").permitAll()
                .antMatchers("/Transaction-Request/tif-image").permitAll()
                .antMatchers("/Transaction-Request/getDocumentTypes/**").permitAll()
                .antMatchers("/Transaction-Request/addSignature").permitAll()
                .antMatchers("/Transaction-Request/softReject").permitAll()
                .antMatchers("/Transaction-Request/softReject-all").permitAll()
                .antMatchers("/Transaction-Request/add-transaction-customer").permitAll()
                .antMatchers("/Transaction-Request/add-customer-to-trasaction-request").permitAll()
                .antMatchers("/Transaction-Request/fund-transaction-within-ndb").permitAll()
                .antMatchers("/Transaction-Request/cash-withdrawal").permitAll()
                .antMatchers("/Transaction-Request/cash-deposit").permitAll()
                .antMatchers("/Transaction-Request/cash-deposit/file-upload").permitAll()
                .antMatchers("/Transaction-Request/cash-deposit/update").permitAll()
                .antMatchers("/Transaction-Request/cash-deposit/signature").permitAll()
                .antMatchers("/Transaction-Request/cash-deposit-denominations").permitAll()
                .antMatchers("/Bank/save").permitAll()
                .antMatchers("/Bank/update").permitAll()
                .antMatchers("/Bank/delete/**").permitAll()
                .antMatchers("/Bank/search-bank/**").permitAll()
                .antMatchers("/Bank/get-all-banks").permitAll()
                .antMatchers("/Branch/addNewBranch").permitAll()
                .antMatchers("/Branch/getAll").permitAll()
                .antMatchers("/Branch/updateBranch").permitAll()
                .antMatchers("/Branch/delete/**").permitAll()
                .antMatchers("/Branch/search-branch-by-bank/**").permitAll()
                .antMatchers("/Branch/search-branch/**").permitAll()
                .antMatchers("/Transaction-Request/fund-transaction-within-ndb/file-upload").permitAll()
                .antMatchers("/Transaction-Request/fund-transaction-within-ndb/update").permitAll()
                .antMatchers("/Transaction-Request/fund-transaction-within-ndb/signature").permitAll()
                .antMatchers("/Transaction-Request/fundtrasnfet-other-slip/update").permitAll()
                .antMatchers("/Currency/save").permitAll()
                .antMatchers("/Currency/update").permitAll()
                .antMatchers("/Currency/delete/**").permitAll()
                .antMatchers("/Currency/search/**").permitAll()
                .antMatchers("/Currency/get-all").permitAll()
                .antMatchers("/CSR-Queue/genarate-token").permitAll()
                .antMatchers("/CSR-Queue/get-queue-list").permitAll()
                .antMatchers("/CSR-Queue/get-hold-queue-list").permitAll()
                .antMatchers("/CSR-Queue/get-pending-queue-list").permitAll()
                .antMatchers("/CSR-Queue/hold-a-queue-number").permitAll()
                .antMatchers("/CSR-Queue/complete-a-queue-number").permitAll()
                .antMatchers("/CSR-Queue/get-completed-queue-list").permitAll()
                .antMatchers("/CSR-Queue/continue-a-hold-token").permitAll()
                .antMatchers("/CSR-Queue/genarate-an-special-queue-number").permitAll()
                .antMatchers("/CSR-Queue/genarate-redundent-token").permitAll()
                .antMatchers("/Teller-Queue/genarate-token").permitAll()
                .antMatchers("/Teller-Queue/get-queue-list").permitAll()
                .antMatchers("/Teller-Queue/get-hold-queue-list").permitAll()
                .antMatchers("/Teller-Queue/get-pending-queue-list").permitAll()
                .antMatchers("/Teller-Queue/hold-a-queue-number").permitAll()
                .antMatchers("/Teller-Queue/complete-a-queue-number").permitAll()
                .antMatchers("/Teller-Queue/get-completed-queue-list").permitAll()
                .antMatchers("/Teller-Queue/continue-a-hold-token").permitAll()
                .antMatchers("/Teller-Queue/genarate-an-special-queue-number").permitAll()
                .antMatchers("/Teller-Queue/genarate-redundent-token").permitAll()
                .antMatchers("/reports/daily-report").permitAll()
                .anyRequest().authenticated();
    }
}
