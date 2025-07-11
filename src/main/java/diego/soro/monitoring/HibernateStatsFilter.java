package diego.soro.monitoring;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HibernateStatsFilter implements Filter {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, jakarta.servlet.ServletException {
        Statistics stats = sessionFactory.getStatistics();
        stats.clear();
        stats.setStatisticsEnabled(true);

        long startTime = System.nanoTime();
        chain.doFilter(req, res);
        long durationMs = (System.nanoTime() - startTime) / 1_000_000;

        System.out.println("🎯 Hibernate Stats for " +
                ((HttpServletRequest) req).getMethod() + " " +
                ((HttpServletRequest) req).getRequestURI() +
                " → Queries: " + stats.getQueryExecutionCount() +
                ", Prepares: " + stats.getPrepareStatementCount() +
                ", Entities fetched: " + stats.getEntityFetchCount() +
                ", Collections fetched: " + stats.getCollectionFetchCount() +
                ", Time: " + durationMs + " ms");
    }
}

