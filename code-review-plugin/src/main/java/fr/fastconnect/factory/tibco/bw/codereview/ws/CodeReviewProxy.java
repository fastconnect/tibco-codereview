package fr.fastconnect.factory.tibco.bw.codereview.ws;


public class CodeReviewProxy implements fr.fastconnect.factory.tibco.bw.codereview.ws.CodeReview_PortType {
  private String _endpoint = null;
  private fr.fastconnect.factory.tibco.bw.codereview.ws.CodeReview_PortType codeReview_PortType = null;
  
  public CodeReviewProxy() {
    _initCodeReviewProxy();
  }
  
  public CodeReviewProxy(String endpoint) {
    _endpoint = endpoint;
    _initCodeReviewProxy();
  }
  
  private void _initCodeReviewProxy() {
    try {
      codeReview_PortType = (new fr.fastconnect.factory.tibco.bw.codereview.ws.CodeReview_ServiceLocator()).getCodeReview();
      if (codeReview_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)codeReview_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)codeReview_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (codeReview_PortType != null)
      ((javax.xml.rpc.Stub)codeReview_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public fr.fastconnect.factory.tibco.bw.codereview.ws.CodeReview_PortType getCodeReview_PortType() {
    if (codeReview_PortType == null)
      _initCodeReviewProxy();
    return codeReview_PortType;
  }
  
  public void review(fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.CodeReview codeReview) throws java.rmi.RemoteException{
    if (codeReview_PortType == null)
      _initCodeReviewProxy();
    codeReview_PortType.review(codeReview);
  }
  
  public void stopEngine() throws java.rmi.RemoteException{
    if (codeReview_PortType == null)
      _initCodeReviewProxy();
    codeReview_PortType.stopEngine();
  }
  
  public void isStarted() throws java.rmi.RemoteException{
    if (codeReview_PortType == null)
      _initCodeReviewProxy();
    codeReview_PortType.isStarted();
  }
  
}