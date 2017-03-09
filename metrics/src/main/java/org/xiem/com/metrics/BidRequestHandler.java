/*
package com.yosemitecloud.rts.main.server.eventhandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Timer.Context;
import java.util.concurrent.TimeUnit;
import com.codahale.metrics.Timer;
import com.google.common.base.Supplier;
import com.yosemitecloud.rts.main.adselection.AdSelector;
import com.yosemitecloud.rts.main.adselection.BidRejectionReason;
import com.yosemitecloud.rts.main.common.ConfigurationKey;
import com.yosemitecloud.rts.main.common.EventType;
import com.yosemitecloud.rts.main.common.ExchangeType;
import com.yosemitecloud.rts.main.rtb.AbstractExchangeAdaptor;
import com.yosemitecloud.rts.main.rtb.abstractinfo.BidInfo;
import com.yosemitecloud.rts.main.rtb.abstractinfo.BidRequestInfo;
import com.yosemitecloud.rts.main.rtb.abstractinfo.BidResponseInfo;
import com.yosemitecloud.rts.main.rtb.abstractinfo.BidSlotInfo;
import com.yosemitecloud.rts.main.rtb.fastno.FastNoEvaluator;
import com.yosemitecloud.rts.main.server.RequestContext;
import com.yosemitecloud.rts.main.server.eventrecord.BidRequestEvent;
import com.yosemitecloud.rts.main.server.eventrecord.EventRecorder;
import com.yosemitecloud.rts.main.server.tracking.UserStoreManager;
import com.yosemitecloud.rts.main.utils.DebugBuffer;
import com.yosemitecloud.rts.main.utils.Metrics;

public class BidRequestHandler extends AbstractEventHandler {//竞价请求处理相关

    public static final String CONTENT_LENGTH = "Content-Length";

    private static final long serialVersionUID = 1560537781520424290L;

    private static final Logger LOG = LoggerFactory.getLogger(BidRequestHandler.class);

    public final ExchangeType exchangeType;
    public final AbstractExchangeAdaptor exchangeAdapter;
    public final DebugBuffer debug;
    
	private static final long USERSTORE_TIMEOUT_THRESHOLD = 60L;
    
	private final Timer userStoreTimer = Metrics.getInstance().timer("userStoreTimeout");

    public BidRequestHandler(final int serverId,
		final Supplier<RequestContext> contextSupplier,
		final ExchangeType type, 
		final AbstractExchangeAdaptor adapter,
		final EventRecorder bidRecorder, 
		final boolean isDebug) {//构造函数
			
        super(serverId, contextSupplier, bidRecorder);
        this.exchangeType = type;
        this.exchangeAdapter = adapter;
        debug = new DebugBuffer(isDebug);
    }

    public static final String JETTY_START_TIME = "jettyStartTime";

    @Override
    protected void doPost(final HttpServletRequest httpRequest, final HttpServletResponse httpResponse) {//竞价处理
        
		final Context timerContext = timer.time();//用于性能监控
       
		final RequestContext requestContext = contextSupplier.get();
        try {
            final long startTime = System.currentTimeMillis();
            
			String requestSizeStr = httpRequest.getHeader(CONTENT_LENGTH);
            
			final int requestSize = NumberUtils.toInt(requestSizeStr, -1);
            
			if (requestSize < 0) {
                httpResponse.setStatus(HttpStatus.SC_BAD_REQUEST); // 400
                return;
            }
            
			debug.reset(debug.isDebug);
            
			BidRequestInfo bidRequestInfo = exchangeAdapter.getBidRequestInfo(httpRequest, requestContext);
            
			final BidResponseInfo bidResponse = new BidResponseInfo(bidRequestInfo, debug.isDebug);

            if (bidRequestInfo == null) {
                httpResponse.setStatus(HttpStatus.SC_BAD_REQUEST); //400
                return;
            }
            debug.appendln("Bid request received: ", bidRequestInfo.toString());
            
            bidRequestInfo.setRequestTime(startTime);//bidRequestInfo was generated, and we log it even though no bid
            
			//read userstore for the user info
            final long storeTimeout = requestContext.configWrapper.getLong(ConfigurationKey.USER_STORE_READ_TIMEOUT, UserStoreManager.BLOCKING_TIMEOUT);
            
			final long before = System.currentTimeMillis();
            bidRequestInfo.user.retrieveProfile(storeTimeout);//????
            final long after = System.currentTimeMillis();
            
			if (after - before > USERSTORE_TIMEOUT_THRESHOLD) {
                userStoreTimer.update(after - before, TimeUnit.MILLISECONDS);
            }
            
			debug.appendln("read from userstore, user:" + bidRequestInfo.user.getUserId());
            if (FastNoEvaluator.fastNo(bidRequestInfo)) {
                // TODO: add fast no response
            }

            //Treat each ad slot as a separate ad request, as of now, we only bid on the first possible slot
            for (BidSlotInfo bidSlot : bidRequestInfo.getBidSlots()) {
                if (bidSlot == null) {
                    continue;
                }
                if (bidSlot.placement == null) {
                    bidRequestInfo.addRejectionReason(BidRejectionReason.NoApplicablePlacement);
                    debug.appendln("No applicable placement");
                } else {
                    AdSelector selector = new AdSelector(bidSlot, requestContext, EventType.BID_REQUEST);
                    BidInfo bid = selector.selectAdToBid(debug);
                    if (bid != null && bid.bidCpi >= bidSlot.minimumBidCpi) {
                        bidSlot.setBidInfo(bid);
                    }
                    if (bidSlot.noBid()) {
                        debug.appendln("AdSelector returns no bid");
                    }
                }
                bidResponse.add(bidSlot);
                exchangeAdapter.sendBidResponse(bidResponse, httpResponse, requestContext);
                //Event Record
                final long processingTime = System.currentTimeMillis() - startTime;

                bidRequestInfo.setBidProcessingTime(processingTime);
                debug.appendln("Bid response sent with processing time ", Long.toString(processingTime));
                eventRecorder.add(new BidRequestEvent(bidSlot));
                return;
            }
            bidRequestInfo.addRejectionReason(BidRejectionReason.NoValidBidSlot);
            debug.appendln("No valid bid slot");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            debug.appendln("Exception: ", e.getMessage());
        } finally {
            timerContext.stop();
            debug.log(LOG);
        }
    }

    @Override
    protected void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        doPost(httpRequest, httpResponse);
    }
}
*/
